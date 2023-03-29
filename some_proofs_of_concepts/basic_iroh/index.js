const Iroh = require("iroh");
const fs = require('fs');
const yaml = require('js-yaml');
const express = require('express')

class RuntimeCall {
  constructor(name) {
    this.name = name;
    this.eval = [];
  }

  setEvaluation(args, returnValue) {
    this.eval.push({args, returnValue})
  }
}

var exceptions = ["log"];

//let project_file_path = "exec.js"
let project_file_path = "express/index.js"


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

  const originalFetch = fetch;
  listener.on("before", function (e) {
    if (e.object === fetch) {
      e.call = function (url) {
        const targetUrl = url === 'localhost' ? 'localhost' : url;
        return originalFetch.call(null, [targetUrl].concat(arguments.slice(1)))
      };
    }});
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
