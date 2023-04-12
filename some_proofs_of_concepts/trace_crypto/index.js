const Injector = require('../injector/injector.js');

var crypto = require('crypto');

const injector = new Injector(crypto, 'randomBytes', __filename, 'SSE23-crypto');

let token = crypto.randomBytes(64).toString('hex');
console.log(token)
