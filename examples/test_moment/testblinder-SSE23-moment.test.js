const moment = require('moment');

describe('blindtester-SSE23-moment', () => {
    test('moment.add(1.0,"days") should returns "2023-10-01T22:00:00.000Z"', () => {
        expect(moment.add(1.0,"days")).toMatchObject("2023-10-01T22:00:00.000Z");
    });
});