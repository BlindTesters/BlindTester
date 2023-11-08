var esprima = require('esprima');
var fs = require('fs');
var escodegen = require('escodegen')

function deepCopy(o){
    return JSON.parse(JSON.stringify(o))
}

function findCalls(node, functionName) {
    if(node === undefined){
        return false
    }

    switch (node.type) {
        case 'ExpressionStatement':
            return node.expression.callee.property.name == functionName
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
            if(node.init.callee.property !== undefined)
                return node.init.callee.property.name == functionName
            break;
        default:
            console.log("Unknown statement type : " + node.type);
            break;
    }

    return false
}

function getAllCalls(scriptPath, functionName) {
    try {
        lstFuncTree = []
        totalBody = []

        const program = fs.readFileSync(scriptPath, 'utf8');
        let tree = esprima.parse(program);
        let body = tree.body

        // if any node of the root body contains the function we keep it
        for (var i = 0; i < body.length; i++) {
            let stmt = body[i]
            totalBody.push(stmt)
            let isInteresting = findCalls(stmt, functionName)
            if(isInteresting) {
                lstFuncTree.push({call: stmt, content: {type: tree.type, body: deepCopy(totalBody)}})
            }
        }

        for (var i = 0; i < lstFuncTree.length; i++) {
            var completeCodeTest = lstFuncTree[i].content
            const bodyCopy = deepCopy(completeCodeTest.body)
            const size = bodyCopy.length
            const bodyHeader = bodyCopy.slice(0, size-1)
            const bodyCallToTest = [bodyCopy[size-1]]

            // generate header
            completeCodeTest.body = bodyHeader
            const codeHeader = escodegen.generate(completeCodeTest)

            // generate test
            completeCodeTest.body = bodyCallToTest
            const codeCallToTest = escodegen.generate(completeCodeTest)

            console.log("Test #" + i + " : ")

            // print the header necessary for the expression to work
            console.log(codeHeader)

            // temprary way to see the expression to test
            console.log("Check value of this call :")
            console.log("************************")
            console.log(codeCallToTest)
            console.log("************************")
            console.log("End of test #"+i)
            console.log("")
        }

        return lstFuncTree
    }
    catch (err) {
        console.error(err)
        return [];
    }
}

getAllCalls('../test_crypto/index.js', 'pbkdf2Sync')
