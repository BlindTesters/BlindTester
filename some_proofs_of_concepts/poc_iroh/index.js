const Iroh = require("iroh");
const fs = require('fs');
const yaml = require('js-yaml');

class RuntimeCall {
  constructor(name) {
    this.name = name;
    this.eval = [];
  }

  setEvaluation(args, returnValue) {
    this.eval.push({args, returnValue})
  }
}

var exceptions = ["require", "log"];

let project_file_path = "exec.js"

fs.readFile(project_file_path, "utf8", (err, data) => {
  if (err) {
    console.error(err);
    return;
  }

  let stage = new Iroh.Stage(data);

  let calls = {};

  // function call
  stage.addListener(Iroh.CALL)
    .on("after", (e) => {
      let func_name = e.name;

      if (!exceptions.includes(func_name)) {
        if (calls[func_name] == null) {
          calls[func_name] = new RuntimeCall(func_name)
        }

        calls[func_name].setEvaluation(e.arguments, e.return)
      }
    });

  // program

  eval(stage.script);

  console.log("Detected calls : ")

  for(var key in calls) {
    var value = calls[key];
    console.log(value);
  }

  console.log("\nGenerate yaml file...")

  let yamlStr = yaml.dump(calls);
  let file_path = "runtime.yaml"
  fs.writeFileSync(file_path, yamlStr, "utf8");
  console.log("Generating done : " + file_path);
});
