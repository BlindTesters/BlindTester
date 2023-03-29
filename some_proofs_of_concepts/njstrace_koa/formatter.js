const Formatter = require('njstrace/lib/formatter');
const path = require('path');

class CustomFormatter extends Formatter {
  constructor(config) {
    super(config);
    this.TRACE = {};
    this.STACK = {};
  }

  // Add the inputs to the STACK object to be able to map the output.
  addToStack(entry) {
    this.STACK[entry.callId] = {
      'inputs': entry.args
    };
  }

  // Add the outputs to the TRACE object.
  addTrace(entry) {
    if (this.TRACE[entry.name] == null) {
      this.TRACE[entry.name] = [];
    }

    this.TRACE[entry.name].push({
      'inputs': this.STACK[entry.callId].inputs,
      'output': entry.returnValue
    });
  }

  // override onEntry to only add the entry to the STACK object.
  onEntry(entry) {
    this.addToStack(entry);
  }

  // override onExit to only keep the entry in the TRACE object and write to a file.
  onExit(entry) {
    // Store the output in the TRACE object.
    this.addTrace(entry);

    // write to a json file with interesting information,
    const project_path = path.dirname(entry.file);
    const main_file = path.basename(entry.file);
    let executed_functions = [];
    Object.entries(this.TRACE).forEach(([key, value]) => {
      executed_functions.push({
        'name': key,
        'calls': value
      });
    });
    let output = {
      'project_name': 'test',
      'project_path': project_path,
      'main_file': main_file,
      'requires': [],
      'executed_functions': executed_functions,
    };
    require('fs').writeFile(this.stdout, JSON.stringify(output), (error) => {
      if (error) throw error;
    });
  }
}

module.exports = CustomFormatter;
