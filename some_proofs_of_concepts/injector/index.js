// Inject a wrapper around the function in the class we want to inspect.
const Injector = require('./injector.js');
const lib_name = 'moment';
const moment = new Injector(require(lib_name), lib_name, 'format', __filename, 'SSE23-derivative').get_library();

const momentInst = moment();

console.log(momentInst.format('MMMM Do YYYY, h:mm:ss a'));
console.log(momentInst.format('dddd'));
console.log(momentInst.format("MMM Do YY"));
console.log(momentInst.format('YYYY [escaped] YYYY'));
console.log(momentInst.format());
