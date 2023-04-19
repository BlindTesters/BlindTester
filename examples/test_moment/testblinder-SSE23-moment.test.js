const JSpector = require('../../JSpector/jspector');
const moment = require('moment');

describe('blindtester-SSE23-moment', () => {
    test('moment.diff("2023-09-30T22:00:00.000Z") should returns 1000.0', () => {
        expect(moment.diff("2023-09-30T22:00:00.000Z")).toBe(1000.0);
    });
    test('moment.diff("2023-09-30T22:00:00.000Z") should returns 60000.0', () => {
        expect(moment.diff("2023-09-30T22:00:00.000Z")).toBe(60000.0);
    });
});