const moment = require('moment');

describe('auto-SSE23-derivative', () => {
test('moment.format [MMMM Do YYYY, h:mm:ss a] should returns April 12th 2023, 1:20:22 pm', () => {
  expect(moment().format('MMMM Do YYYY, h:mm:ss a')).toBe('April 12th 2023, 1:20:22 pm');
});
test('moment.format [dddd] should returns Wednesday', () => {
expect(moment().format('dddd')).toBe('Wednesday');
});
test('moment.format [MMM Do YY] should returns Apr 12th 23', () => {
expect(moment().format('MMM Do YY')).toBe('Apr 12th 23');
});
test('moment.format [YYYY [escaped] YYYY] should returns 2023 escaped 2023', () => {
expect(moment().format('YYYY [escaped] YYYY')).toBe('2023 escaped 2023');
});
});
