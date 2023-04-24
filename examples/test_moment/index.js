// Inject a wrapper around the function in the class we want to inspect.
const JSpector = require('../../JSpector/jspector');
const moment = new JSpector(
  'moment',
  'add',
  __filename,
  'SSE23-moment'
).get_library();

var date1 = moment('2023-10-01 00:00:00').add(1, 'days')
console.log(date1)

var date2 = moment('2023-10-01 00:00:00').add(1, 'month')
console.log(date1)

// 1s => 1000 ms
var date1 = moment('2023-10-01 00:00:00');
var date2 = moment('2023-10-01 00:00:01');
var diff = date2.diff(date1);

console.log(diff)
