const Iroh = require("iroh");
const fs = require('fs');

class RuntimeCall {
  constructor(name) {
    this.name = name;
    this.eval = [];
  }

  setEvaluation(args, returnValue) {
    this.eval.push({ args, returnValue })
  }
}

var exceptions = ["log"];

//let project_file_path = "exec.js"
let project_file_path = "index.js"

let data = fs.readFileSync(project_file_path, "utf8", (err, data) => {
  if (err) {
    console.error(err);
    return;
  }

  return data;
});


let stage = new Iroh.Stage(data);

let calls = {};

// function call
stage.addListener(Iroh.CALL)
  .on("after", (e) => {
    if (e != null) {
      let func_name = e.name;
      if (e != null && !exceptions.includes(func_name)) {
        if (calls[func_name] == null) {
          calls[func_name] = new RuntimeCall(func_name)
        }

        calls[func_name].setEvaluation(e.arguments, e.return)
      }
    }

  });

// Eval program

try {
  eval(stage.script);
} catch (error) {
  console.error(error);
}

console.log("Detected calls : ")

for (var key in calls) {
  let value = calls[key];
  console.log(value);
}

console.log("\nGenerate JSON trace file...")

let json = JSON.stringify(calls)
let file_path = "trace.json"

fs.writeFileSync(file_path, json, "utf8");
console.log("Generating done : " + file_path);
