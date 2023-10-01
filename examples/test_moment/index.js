const moment = require('moment');

var date1 = moment('2023-10-01 00:00:00').add(1, 'days')
console.log(date1)

var date2 = moment('2023-10-01 00:00:00').add(1, 'month')
console.log(date1)

// 1s => 1000 ms
var date1 = moment('2023-10-01 00:00:00');
var date2 = moment('2023-10-01 00:00:01');
var diff = date2.diff(date1);

console.log(diff)
