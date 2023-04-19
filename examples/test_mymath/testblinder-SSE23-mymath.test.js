const JSpector = require('../../JSpector/jspector');
const my_math = require('./my_math');

describe('blindtester-SSE23-mymath', () => {
    test('my_math.inc(1.0) should returns 2.0', () => {
        expect(my_math.inc(1.0)).toBe(2.0);
    });
    test('my_math.inc(1.0,1.0) should returns 2.0', () => {
        expect(my_math.inc(1.0,1.0)).toBe(2.0);
    });
});