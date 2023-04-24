// Inject a wrapper around the function in the class we want to inspect.
const JSpector = require('../../JSpector/jspector');
const math = new JSpector(
  'mathjs',
  'derivative',
  __filename,
  'SSE23-derivative'
).get_library();

let a = math.derivative('x^2', 'x');

console.log(a)
