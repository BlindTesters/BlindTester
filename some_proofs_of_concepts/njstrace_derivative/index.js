const Injector = require('./injector.js');

const math = require('mathjs')
const injector = new Injector(math, 'math.derivative', __filename, 'SSE23-derivative');

let a = math.derivative('x^2', 'x');
let b = math.derivative('sin(2x)', 'x')

console.log(a)
console.log(b)
