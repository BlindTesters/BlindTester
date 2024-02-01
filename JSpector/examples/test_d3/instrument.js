// Inject a wrapper around the function in the class we want to inspect.
const JSpector = require('../../JSpector/jspector');

const jspector = new JSpector(
  'd3',
  'selection.attr',
  'SSE23-d3node'
);

jspector.start();