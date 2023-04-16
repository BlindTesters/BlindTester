// Inject a wrapper around the function in the class we want to inspect.
const Injector = require('../injector/injector.js');
const lib_name = 'crypto';
const crypto = new Injector(require(lib_name), lib_name, 'pbkdf2Sync', __filename, 'SSE23-crypto').get_library();

let hash = crypto.pbkdf2Sync("seg-2023", "salt", 1000, 64, "sha512");

console.log(typeof hash)

console.log(hash)
