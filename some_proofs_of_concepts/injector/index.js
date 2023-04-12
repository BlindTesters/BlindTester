const Injector = require('./injector.js');

const moment = require('moment')
const momentInst = moment();
const injector = new Injector(momentInst, 'format', __filename, 'SSE23-derivative');

console.log(momentInst.format('MMMM Do YYYY, h:mm:ss a'));
console.log(momentInst.format('dddd'));
console.log(momentInst.format("MMM Do YY"));
console.log(momentInst.format('YYYY [escaped] YYYY'));
console.log(momentInst.format());
