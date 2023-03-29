const path = require('path');
const CustomFormatter = require('./formatter');
const CustomTracer = require('./tracer');

// Get the relative path from the working directory to the directory of the main app file
const rel = path.relative(process.cwd(), __dirname);

// Build the glob pattern for all JS files one that excludes nodes_modules, and use those
const allJsFiles = path.join(rel, '**', '*.js');
const noNodeModules = '!' + path.join(rel, '**', 'node_modules', '**', '*.js');
const glob = [allJsFiles, noNodeModules];

// Inject the tracer into the app
const njstrace = require('njstrace').inject({
  // Use the custom formatter to write the trace to a json file
  formatter: new CustomFormatter({ 'stdout': 'out.json' }),
  files: glob
});
// Replace the default tracer with the custom tracer
njstrace.tracer = new CustomTracer(njstrace.getFormatters(njstrace.config.formatter));
module.exports = { njstrace };
