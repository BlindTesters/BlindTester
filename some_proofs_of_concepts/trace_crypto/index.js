// Inject a wrapper around the function in the class we want to inspect.
const Injector = require('../injector/injector.js');
const lib_name = 'crypto';
const crypto = new Injector(require(lib_name), lib_name, 'randomBytes', __filename, 'SSE23-crypto').get_library();

let token = crypto.randomBytes(64).toString('hex');
console.log(token)
