const my_math = require('./my_math');

describe('blindtester-my_math.inc', () => {
    test('my_math.inc(1.0) should returns 2.0', () => {
        expect(my_math.inc(1.0)).toBe(2.0);
    });
    test('my_math.inc(1.0,2.0) should returns 3.0', () => {
        expect(my_math.inc(1.0,2.0)).toBe(3.0);
    });
});