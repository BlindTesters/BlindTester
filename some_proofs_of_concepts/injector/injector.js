const acorn = require('acorn');
const fs = require('fs');
const path = require('path');
const walk = require('acorn-walk');


class Injector {
  /**
   * 
   * @param {*} object The object in which the function to trace is located.
   * @param {*} fn The function to trace.
   * @param {*} main_file The main file of the project.
   * @param {*} project_name The name of the project.
   * @param {*} [output_file_name] The name of the output file (default='trace.json').
   */
  constructor(object, fn, main_file, project_name, output_file_name='trace.json') {
    // This will contain the trace.
    this.TRACE = {};
    // This will contain all the required modules in the main file.
    this.REQUIRES = [];
    // Assign the parameters.
    this.object = object;
    this.fn = fn;
    this.main_file = main_file;
    this.project_name = project_name;
    this.output_file_name = output_file_name;

    // Extract the requires from the main file.
    this.extractRequires();

    // Wrap the goal function.
    this.wrapFunction();

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
                console.log(property.value.name, requiredModule)
              });
            } 
            // regular imports
            else {
              // Ignore undefined variables as well as this library.
              if (variableName != undefined && !requiredModule.endsWith('injector.js')) {
                outerThis.REQUIRES.push({
                  'include': `const ${variableName} = require(${requiredModule});`
                });
              }
            }
          }
        });
      }
    });
  }

  /**
   * This function will keep a trace of the provided input and output for
   * the target function.
   * @param {*} _inputs The inputs provided to the target function.
   * @param {*} _output The output of the target function.
   */
  addTrace(_inputs, _output) {
    // Build the function name.
    const funcName = `${this.object.constructor.name}.${this.fn}`;

    // Initialize the array if it does not exist yet.
    if (this.TRACE[funcName] === undefined) {
      this.TRACE[funcName] = [];
    }

    this.TRACE[funcName].push({
      'inputs': Object.values(_inputs),
      'output': _output
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
    // TODO remove this or add a verbose mode
    // Pretty print the output to the console.
    console.log('Trace content');
    console.log('------------');
    console.log(JSON.stringify(output, null, 2));
    console.log('------------');
    // Write the output to a file snychronously (to make sure it is written before the process exits).
    fs.writeFileSync(this.output_file_name, JSON.stringify(output), () => {
      if (error) throw error;
    });
  }

  /**
   * This function will wrap the target function and keep a trace of the
   * inputs and outputs.
   */
  wrapFunction() {
    // Keep a reference to the original function and the current "this".
    const originalFunction = this.object[this.fn];
    const outerThis = this;
    // Wrap the function.
    this.object[this.fn] = function () {
      // TODO remove this or add a verbose mode
      console.log(`Executing wrapped "${outerThis.fn}"....`);
      // Call the original function, store trace and return result.
      const result = originalFunction.apply(this, arguments);
      outerThis.addTrace(arguments, result);
      return result;
    }
    // Bind the target object to the function (to allow "this" keyword to work in object).
    .bind(this.object);
  }
}

module.exports = Injector;
