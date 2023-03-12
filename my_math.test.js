const sum = require('./my_math');

test('adds 1 + 2 + 1 to equal 4', () => {
  expect(sum(1, 2, 1)).toBe(4);
});
