const Injector = require('./injector.js');

var crypto = require('crypto');

const injector = new Injector(crypto, 'randomBytes', __filename, 'SSE23-crypto');

let token = crypto.randomBytes(64).toString('hex');
console.log(token)

injector.writeTrace();
