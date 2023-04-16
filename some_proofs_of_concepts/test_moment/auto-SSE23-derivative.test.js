const moment = require('moment');

describe('auto-SSE23-derivative', () => {
test('moment.format [MMMM Do YYYY, h:mm:ss a] should returns "April 16th 2023, 4:32:45 pm"', () => {
expect(moment.format('MMMM Do YYYY, h:mm:ss a')).toBe("'April 16th 2023, 4:32:45 pm'");
});
test('moment.format [dddd] should returns "Sunday"', () => {
expect(moment.format('dddd')).toBe("'Sunday'");
});
test('moment.format [MMM Do YY] should returns "Apr 16th 23"', () => {
expect(moment.format('MMM Do YY')).toBe("'Apr 16th 23'");
});
test('moment.format [YYYY [escaped] YYYY] should returns "2023 escaped 2023"', () => {
expect(moment.format('YYYY [escaped] YYYY')).toBe("'2023 escaped 2023'");
});
test('moment.format [] should returns "2023-04-16T16:32:45+02:00"', () => {
expect(moment.format()).toBe("'2023-04-16T16:32:45+02:00'");
});
});