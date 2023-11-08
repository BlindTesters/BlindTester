import esprima

f = open("index.js", "r")
index = f.read()

tree = esprima.parse(index)

def keepOnlyInterestingNodes(tree, functionName):
    for node in tree.body:
        if node.type == "FunctionDeclaration":
            if node.id.name == functionName:
                print(node)


keepOnlyInterestingNodes(tree, "testABC")
