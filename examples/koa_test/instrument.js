// Inject a wrapper around the function in the class we want to inspect.
const JSpector = require('../../JSpector/jspector');

const jspector = new JSpector(
  '@koa/router',
  'middleware',
  'SSE23-koa'
);

jspector.start();