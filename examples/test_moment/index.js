// Inject a wrapper around the function in the class we want to inspect.
const JSpector = require('../../JSpector/jspector');

const lib_name = 'moment';
const moment = new JSpector(require(lib_name), lib_name, 'diff', __filename, 'SSE23-moment').get_library();

// 1s => 1000 ms
var date1 = moment('2023-10-01 00:00:00');
var date2 = moment('2023-10-01 00:00:01');
var diff = date2.diff(date1);

console.log(diff)

// 1 min => 60'000 ms
date1 = moment('2023-10-01 00:00:00');
date2 = moment('2023-10-01 00:01:00');
diff = date2.diff(date1);

console.log(diff)
