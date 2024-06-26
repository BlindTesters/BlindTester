const acorn = require('acorn');
const fs = require('fs');
const path = require('path');
const Module = require('module');
const walk = require('acorn-walk');
const escodegen = require('escodegen');
const esprima = require('esprima');

class JSpector {
    /**
     *
     * @param {*} library_name The library name in which the function to trace is located.
     * @param {*} fn The function to trace.
     * @param {*} project_name The name of the project.
     * @param {*} [output_file_name] The name of the output file (default='trace.json').
     */
    constructor(library_name, fn, project_name, verbose=false, output_file_name='trace.json') {
        // This will contain the trace.
        this.TRACE = {};
        // This will contain all the required modules in the main file.
        this.REQUIRES = [];

        // Set variables useful for trace generation.
        this.main_file = process.argv[1];  // this is the file executed by node
        this.project_name = project_name;
        this.output_file_name = output_file_name;
        this.verbose = verbose;

        // Import the desired library.
        this.library_name = library_name;
        this.import_name = this.library_name.split('/').pop();
        this.library = this.requireLibrary(this.library_name);

        // Compute the function name and path (function might be in a submodule)
        this.fn = fn;
        this.pathFn = this.fn.split('.').slice(0, -1);
        this.fnName = this.fn.split('.').pop();

        // Extract the requires from the main file.
        this.extractRequires();

        // Wrap the goal function.
        this.wrapFunction();

        // read ast
        this.buildTree();

        // Detect SIGINT and call process.exit manually to make sure
        // it is called.
        process.on('SIGINT', (code) => {
            process.exit();
        });

        process.on('exit', (code) => {
            console.log(`Save trace to ${this.output_file_name}...`);
            this.writeTrace();
            console.log(`Trace saved. Exit...`);
        });
    }

    /**
     * Build the path to the targeted file. This helpls to retrieve the
     * available modules in the targeted file's context.
     * @returns the path to the targeted file.
     */
    getLibraryPath() {
        return `${path.dirname(this.main_file)}/`;
    }

    /**
     * This function will load the desired function from the main_file directory by
     * using a require function created with the main_file directory as base.
     * @returns the library.
     */
    requireLibrary() {
        const requireUtil = Module.createRequire(this.getLibraryPath());
        return requireUtil(this.library_name);
    }

    /**
     * This function will go through the main file and extract all the requires.
     */
    extractRequires() {
        // TODO improve somewhat this function if needed, it is not perfect.
        // Extract the requires from the main file.
        const code = acorn.parse(fs.readFileSync(this.main_file, 'utf8'), {ecmaVersion: 2020});
        // Keep a reference to the outer this to use in the callback
        let outerThis = this;
        // Extract the requires from the main file.
        walk.simple(code, {
            VariableDeclaration(node) {
                node.declarations.forEach(declaration => {
                    if (declaration.init && declaration.init.type === 'CallExpression' && declaration.init.callee.name === 'require') {
                        const variableName = declaration.id.name;
                        const requiredModule = declaration.init.arguments[0].value;
                        // todo: handle const { sum, diff } = require('./functions'); in next if
                        if (declaration.id.type === 'ObjectPattern') {
                            declaration.id.properties.forEach(property => {
                                console.log(property.value.name, requiredModule);
                            });
                        }
                        // regular imports
                        else {
                            // Ignore undefined variables as well as this library.
                            if (variableName != undefined && (!requiredModule.endsWith('jspector') && !requiredModule.endsWith('jspector.js'))) {
                                outerThis.REQUIRES.push({
                                    'include': `const ${variableName} = require('${requiredModule}');`
                                });
                            }
                        }
                    }
                });
            }
        });
    }

    deepCopy(obj) {
        return JSON.parse(JSON.stringify(obj));
    }

    // The following 2 methods are very experimental and are only to be used as a POC!!
    flattenNode(node) {
        if (node.type === 'ForStatement') {
            const flattenedCode = [];
            const body = node.body;  // Extract loop body
            const initialization = node.init;  // Extract initialization
            const condition = node.test;  // Extract condition
            const update = node.update;  // Extract update

            let i = initialization.declarations[0].init.value;  // Extract initialization value

            // Repeat the loop body according to condition
            for (i; eval(`${i} ${condition.operator} ${condition.right.value}`); eval(`${update.argument.name}${update.operator}`)) {
                let iterationBody = [];
                for (let j = 0; j <= i; j++) {
                    iterationBody.push(...body.body);
                }
                // Add the loop body code to the flattened code
                flattenedCode.push(iterationBody);
            }

            return flattenedCode;
        }
        return node;
    }

    buildTree() {
        // read ast
        this.lstFuncTree = this.getAllCalls(this.main_file, this.fnName);
        this.ast = [];

        // for each statement / AST, show the header to finally use the statement
        for (let i = 0; i < this.lstFuncTree.length; i++) {
            let completeCodeTest = this.lstFuncTree[i].content;
            const flattenedNode = this.flattenNode(this.lstFuncTree[i].call);
            const bodyCopy = this.deepCopy(completeCodeTest.body);
            const size = bodyCopy.length;
            // remove the final call to the function
            const bodyHeader = bodyCopy.slice(0, size-1);

            // generate header depending on the type of the node.
            if (Array.isArray(flattenedNode)) {
                // for loops: generate a header for each iteration.
                for(let j = 0; j < flattenedNode.length; j++) {
                    let loopBody = this.deepCopy(bodyHeader);
                    loopBody.push(...flattenedNode[j]);
                    completeCodeTest.body = loopBody;
                    this.ast.push(escodegen.generate(completeCodeTest));
                }
            } else {
                completeCodeTest.body = bodyHeader;
                this.ast.push(escodegen.generate(completeCodeTest));
            }
        }
    }

    /**
     * This function will keep a trace of the provided input and output for
     * the target function.
     * @param {*} _inputs The inputs provided to the target function.
     * @param {*} _output The output of the target function.
     */
    addTrace(_inputs, _output) {
        // Build the function name.
        const funcName = `${this.import_name}.${this.fn}`;

        // Initialize the array if it does not exist yet.
        if (this.TRACE[funcName] === undefined) {
            this.TRACE[funcName] = [];
        }

        this.TRACE[funcName].push({
            'inputs': Object.values(_inputs),
            'output': _output,
            'header': this.ast.shift()
        });
    }

    /**
     * This function will write the trace to a file.
     */
    writeTrace() {
        // Build the executed functions array.
        let executed_functions = [];
        Object.entries(this.TRACE).forEach(([key, value]) => {
            executed_functions.push({
                'name': key,
                'calls': value
            });
        });
        // Build the whole output object.
        const output = {
            'project_name': this.project_name,
            'project_path': path.dirname(this.main_file),
            'main_file': path.basename(this.main_file),
            'requires': this.REQUIRES,
            'executed_functions': executed_functions,
        };

        if (this.verbose) {
            console.log('Trace content');
            console.log('------------');
            console.log(JSON.stringify(output, null, 2));
            console.log('------------');
        }

        // Write the output to a file snychronously (to make sure it is written before the process exits).
        fs.writeFileSync(this.output_file_name, JSON.stringify(output), () => {
            if (error) throw error;
        });
    }

    /**
     * This function will return the path to the target function.
     * @returns The path to the target function.
     */
    get_library_path() {
        let lib = this.library;
        this.pathFn.forEach(path => {
            lib = lib[path];
        });
        return lib;
    }

    /**
     * This function will wrap the target function and keep a trace of the
     * inputs and outputs.
     */
    wrapFunction() {
        // Keep a reference to the original function and the current "this".
        let originalFunction = undefined;
        let libPath = this.get_library_path();
        if (libPath.prototype === undefined) {
            originalFunction = libPath[this.fnName];
        } else {
            originalFunction = libPath.prototype[this.fnName];
        }
        const outerThis = this;
        const newFunction = function () {

            if(this.verbose) {
                console.log(`Executing wrapped "${outerThis.fn}"....`);
            }

            // Call the original function, store trace and return result.
            const result = originalFunction.apply(this, arguments);
            outerThis.addTrace(arguments, result);
            return result;
        };

        // Wrap the function depending on the library workflow.
        let wrapped = false;
        if (libPath.prototype === undefined) {
            if (libPath[this.fnName].set === undefined) {
                // Handle libraries that do not use a "setter" property to define
                // the function we'd like to wrap.
                libPath = {...libPath, [this.fnName]: newFunction};
                this.library = libPath;
                wrapped = true;
            } else{
                // Handle libraries that use a "setter" property to define the
                // function we'd like to wrap.
                libPath[this.fnName] = newFunction;
                wrapped = true;
            }
        } else {
            // Handle simplest case libraries.
            libPath.prototype[this.fnName] = newFunction;
            wrapped = true;
        }

        // Warn the user if the function could not be wrapped.
        if (!wrapped) {
            throw new Error(`Could not wrap function ${this.fn}, this library might be incompatible...`);
        }

    }

    /**
     * This function will return the library wrapped by the injector.
     * @returns The library wrapped by the injector.
     */
    get_library() {
        return this.library;
    }

    /**
     * This function will "start" the injector by replacing the library
     * in the node requirements cache.
     */
    start() {
        // Locate the desired library in the paths.
        let libraryPath = require.resolve(this.library_name, {paths:[this.getLibraryPath()]});

        // Try to replace the library in the cache.
        try {
          require.cache[libraryPath].exports = this.get_library();
        } catch(error) {
          // If the library is not in the cache, we need to replace the require function
          if (error instanceof TypeError) {
            const originalRequire = Module.prototype.require;
            const outerThis = this;
            
            Module.prototype.require = function (path) {
              if (path === libraryPath) {
                return outerThis.get_library();
              }
              return originalRequire.apply(outerThis, arguments);
            };
          }
          // Not handled error.
          else {
            console.log(`Could not find the library ${this.library_name} in the cache...`);
            // rethrow the error
            throw error;
          }
        }
    }


    // Find recursively the useful nodes
    findCalls(node, functionName) {
        if(node === undefined){
            return false;
        }

        switch (node.type) {
        case 'ExpressionStatement':
            if(node.expression.callee !== undefined) {
                if (node.expression.callee.name !== undefined) {
                    return node.expression.callee.name == functionName;
                } else{
                    return  node.expression.callee.property.name == functionName;
                }
            }
            else if(node.expression.right !== undefined) {
                if(node.expression.right.callee !== undefined && node.expression.right.callee.property !== undefined){
                    return node.expression.right.callee.property.name == functionName;
                }
            }

            break;
        case 'IfStatement':
            return this.findCalls(node.consequent, functionName) || this.findCalls(node.alternate, functionName);
            break;
        case 'BlockStatement':
            var found = false;

            // check for all stmts
            for(var i = 0;i < node.body.length; i++) {
                found = found | this.findCalls(node.body[i], functionName);
            }

            return found;
            break;
        case 'WhileStatement':
        case 'ForStatement':
            return this.findCalls(node.body, functionName);
            break;
        case 'VariableDeclaration':
            var found = false;
            // check for all stmts
            for(var i = 0;i < node.declarations.length; i++) {
                found = found | this.findCalls(node.declarations[i], functionName);
            }

            return found;
            break;
        case 'VariableDeclarator':
            if(node.init.callee !== undefined){
                if (node.init.callee.property !== undefined) {
                    return node.init.callee.property.name == functionName;
                }
                else if (node.init.callee.name !== undefined) {
                    return node.init.callee.name == functionName;
                }
            }

            break;
        default:
            console.log("Unknown statement type : " + node.type);
            break;
        }

        return false;
    }

    // Create a list of tuples that contain the statement
    // and the associated AST to call the statement with the same
    // values as in the runtime execution
    getAllCalls(scriptPath, functionName) {
        try {
            const lstFuncTree = [];
            const totalBody = [];
            const program = fs.readFileSync(scriptPath, 'utf8');
            const tree = esprima.parse(program);
            const body = tree.body;

            // if any node of the root body contains the function we keep it
            for (var i = 0; i < body.length; i++) {
                const stmt = body[i];
                totalBody.push(stmt);
                const isInteresting = this.findCalls(stmt, functionName);
                if(isInteresting) {
                    lstFuncTree.push({call: stmt, content: {type: tree.type, body: this.deepCopy(totalBody)}});
                }
            }

            return lstFuncTree;
        }
        catch (err) {
            console.error(err);
            return [];
        }
    }
}

module.exports = JSpector;
