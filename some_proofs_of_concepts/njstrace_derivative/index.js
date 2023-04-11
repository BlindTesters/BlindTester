const Injector = require('./injector.js');

const math = require('mathjs');

console.log(math.derivative('x^2', 'x'));

console.log(math["derivative"]);

const injector = new Injector(math, 'derivative', __filename, 'SSE23-derivative');

console.log(math.derivative('x^2', 'x'));

let a = math.derivative('x^2', 'x');
let b = math.derivative('sin(2x)', 'x')

console.log(a)
console.log(b)
