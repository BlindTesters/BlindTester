import * as esprima from 'esprima';
import * as fs from 'fs';

// Function to create a deep copy of an object
function deepCopy(o){
    return JSON.parse(JSON.stringify(o))
}

// Find recursively the useful nodes
function findCalls(node, functionName) {
    if(node === undefined){
        return false
    }

    switch (node.type) {
        case 'ExpressionStatement':
            if(node.expression.callee !== undefined) {
                return  node.expression.callee.property.name == functionName
            }
            else if(node.expression.right !== undefined) {
                if(node.expression.right.callee.property !== undefined){
                    return node.expression.right.callee.property.name == functionName
                }
            }

            break;
        case 'IfStatement':
            return findCalls(node.consequent, functionName) || findCalls(node.alternate, functionName)
            break;
        case 'BlockStatement':
            var found = false

            // check for all stmts
            for(var i = 0;i < node.body.length; i++) {
                found = found | findCalls(node.body[i], functionName)
            }

            return found
            break;
        case 'WhileStatement':
        case 'ForStatement':
            return findCalls(node.body, functionName)
            break;
        case 'VariableDeclaration':
            var found = false
            // check for all stmts
            for(var i = 0;i < node.declarations.length; i++) {
                found = found | findCalls(node.declarations[i], functionName)
            }

            return found
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

    return false
}

// Create a list of tuples that contain the statement and the associated AST to call the statement with the same
// values as in the runtime execution
function getAllCalls(scriptPath, functionName) {
    try {
        const lstFuncTree = []
        const totalBody = []
        const program = fs.readFileSync(scriptPath, 'utf8');
        const tree = esprima.parse(program);
        const body = tree.body

        // if any node of the root body contains the function we keep it
        for (var i = 0; i < body.length; i++) {
            const stmt = body[i]
            totalBody.push(stmt)
            const isInteresting = findCalls(stmt, functionName)
            if(isInteresting) {
                lstFuncTree.push({call: stmt, content: {type: tree.type, body: deepCopy(totalBody)}})
            }
        }

        return lstFuncTree
    }
    catch (err) {
        console.error(err)
        return [];
    }
}

export {getAllCalls, deepCopy}
