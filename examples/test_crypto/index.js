// Inject a wrapper around the function in the class we want to inspect.
const JSpector = require('../../JSpector/jspector');

const lib_name = 'crypto';
const crypto = new JSpector(require(lib_name), lib_name, 'pbkdf2Sync', __filename, 'SSE23-crypto').get_library();

// create a hash of text
let hash = crypto.pbkdf2Sync("seg-2023", "salt", 1000, 64, "sha512");

console.log("Hash : " + hash.toString('hex'));
