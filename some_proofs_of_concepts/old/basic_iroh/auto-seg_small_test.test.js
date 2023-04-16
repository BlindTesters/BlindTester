const my_math = require('./my_math');
const my_vectors = require('./my_vectors');
const my_hello = require('./my_hello');

describe('auto-seg_small_test', () => {
test('my_math.doubler [2.0] should returns 4.0', () => {
expect(my_math.doubler(2.0)).toBe(4.0);
});
test('my_math.doubler [4.0] should returns 8.0', () => {
expect(my_math.doubler(4.0)).toBe(8.0);
});
test('my_hello.sayHello [Test] (void) should not cause exception', () => {
expect(my_hello.sayHello('Test')).toBe(undefined);
});
});