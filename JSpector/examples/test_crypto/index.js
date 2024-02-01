const crypto = require('crypto');

const printHash = (hash) => {
    console.log(`Hash : ${hash.toString('hex')}`);
}

var a = 0;

// compute a hash with salt set to "salt0"
var hash = crypto.pbkdf2Sync("seg-2023", "salt0", 1000, 64, "sha512");

a += 1;
// compute a hash with salt set to "salt1"
var hash_2 = crypto.pbkdf2Sync("seg-2023", `salt${a}`, 1000, 64, "sha512");

a += 1;
// compute a hash with salt set to "salt2"
var hash_3 = crypto.pbkdf2Sync("seg-2023", `salt${a}`, 1000, 64, "sha512");

// print 500 hash
// call a second time with salt0 and other one with salt"i"
for (var i=0; i < 50; i++) {
    // create a hash of text
    hash = crypto.pbkdf2Sync("seg-2023", "salt"+a, 1000, 64, "sha512");
    a+=1;
}
