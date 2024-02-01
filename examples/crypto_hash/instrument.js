// Inject a wrapper around the function in the class we want to inspect.
const JSpector = require('../../JSpector/jspector.js');

const jspector = new JSpector(
  'crypto',
  'pbkdf2Sync',
  'SSE23-crypto'
);

jspector.start();
