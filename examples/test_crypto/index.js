// Inject a wrapper around the function in the class we want to inspect.
const JSpector = require('../../JSpector/jspector');

const lib_name = 'crypto';
const crypto = new JSpector(require(lib_name), lib_name, 'pbkdf2Sync', __filename, 'SSE23-crypto').get_library();

// compute a hash with salt set to "salt0"
let hash = crypto.pbkdf2Sync("seg-2023", "salt0", 1000, 64, "sha512");
console.log("Hash : " + hash.toString('hex'));

// print 100 hash
// call a second time with salt0 and other one with salt"i"
for (var i=0; i < 100; i++) {
  // create a hash of text
  let hash = crypto.pbkdf2Sync("seg-2023", "salt"+i, 1000, 64, "sha512");
  console.log("Hash : " + hash.toString('hex'));
}
