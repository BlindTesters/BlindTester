const math = require('mathjs');

let a = math.derivative('x^2', 'x');

console.log(a)
console.log(JSON.parse(JSON.stringify(a)))
