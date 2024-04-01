const crypto = require('crypto');

var i = 0;

// compute 50 hash
while (i < 50) {
    let hash = crypto.pbkdf2Sync("seg-2023", "salt"+i, 1000, 64, "sha512");
    i++;
}