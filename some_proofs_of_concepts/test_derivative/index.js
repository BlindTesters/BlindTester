// Inject a wrapper around the function in the class we want to inspect.
const Injector = require('../injector/injector.js');
const lib_name = 'mathjs';
const math = new Injector(require(lib_name), lib_name, 'derivative', __filename, 'SSE23-derivative').get_library();

let a = math.derivative('x^2', 'x');
// math.derivative('sin(2x)', 'x')

console.log(a)
