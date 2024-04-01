// Inject a wrapper around the function in the class we want to inspect.
const JSpector = require('../../JSpector/jspector');

const jspector = new JSpector(
  __filename,
  'inc',
  'SSE23-mymath'
);

jspector.start();
