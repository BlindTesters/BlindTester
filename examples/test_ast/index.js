import {getAllCalls, deepCopy} from '../../JStorian/jstorian.mjs';
import * as escodegen from 'escodegen';

const lstFuncTree = getAllCalls('../test_crypto/index.js', 'pbkdf2Sync')

// for each statement / AST, show the header to finally use the statement
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
  console.log("**************************")
  console.log(codeCallToTest)
  console.log("**************************")
  console.log("End of test #"+i)
  console.log("")
}
