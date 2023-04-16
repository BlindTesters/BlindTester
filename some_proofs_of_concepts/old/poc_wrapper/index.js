const process = require('process');
const { wrap } = require('./wrap');

// Load all functions from provided file.
const functions = require(process.argv[2]);
// Parse arguments that we should pass to the function.
// TODO: use somewhat dynamic inputs for each function (yaml file?).
const args = JSON.parse(process.argv[3]);

// Iterate over every function in the object, wrap the function and run it.
Object.values(functions).forEach((_f) => {
    var wrappedFunc = wrap(_f);
    args.forEach(_a => {
        // Execute wrapped function with arguments.
        console.log(wrappedFunc(..._a));
    });
});
