const crypto = require('crypto');

// compute 50 hash
for (var i=0; i < 50; i++) {
    hash = crypto.pbkdf2Sync("seg-2023", "salt"+i, 1000, 64, "sha512");
}
