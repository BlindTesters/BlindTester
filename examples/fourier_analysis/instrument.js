// Inject a wrapper around the function in the class we want to inspect.
const JSpector = require('../../JSpector/jspector');

const jspector = new JSpector(
  'decibels',
  'fromGain',
  'SSE23-fourier'
);

jspector.start();
