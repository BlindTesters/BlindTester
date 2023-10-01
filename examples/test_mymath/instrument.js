// Inject a wrapper around the function in the class we want to inspect.
const JSpector = require('../../JSpector/jspector');

const jspector = new JSpector(
  './my_math',
  'inc',
  'SSE23-mymath'
);

jspector.start();
