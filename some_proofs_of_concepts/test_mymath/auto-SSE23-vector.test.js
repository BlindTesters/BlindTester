const my_vectors = require('./my_vectors');
const my_math = require('./my_math');

describe('auto-SSE23-vector', () => {
    test('my_math.div1(5.0,5.0) should returns 1.0', () => {
        expect(my_math.div1(5.0,5.0)).toBe(1.0);
    });
    test('my_math.div1(5.0,5.0) should returns 1.0', () => {
        expect(my_math.div1(5.0,5.0)).toBe(1.0);
    });
    test('my_math.div1(1.0,4.0) should returns 0.25', () => {
        expect(my_math.div1(1.0,4.0)).toBe(0.25);
    });
    test('my_math.div1(1.0,2.0) should returns 0.5', () => {
        expect(my_math.div1(1.0,2.0)).toBe(0.5);
    });
    test('my_math.div1(1.0,2.0) should returns 0.5', () => {
        expect(my_math.div1(1.0,2.0)).toBe(0.5);
    });
    test('my_math.div1(1.0,2.0) should returns 0.5', () => {
        expect(my_math.div1(1.0,2.0)).toBe(0.5);
    });
    test('my_math.div1(2.0,2.0) should returns 1.0', () => {
        expect(my_math.div1(2.0,2.0)).toBe(1.0);
    });
    test('my_math.div1(3.0,2.0) should returns 1.5', () => {
        expect(my_math.div1(3.0,2.0)).toBe(1.5);
    });
    test('my_math.div1(4.0,2.0) should returns 2.0', () => {
        expect(my_math.div1(4.0,2.0)).toBe(2.0);
    });
    test('my_math.div1(5.0,2.0) should returns 2.5', () => {
        expect(my_math.div1(5.0,2.0)).toBe(2.5);
    });
    test('my_math.div1(6.0,2.0) should returns 3.0', () => {
        expect(my_math.div1(6.0,2.0)).toBe(3.0);
    });
    test('my_math.div1(7.0,2.0) should returns 3.5', () => {
        expect(my_math.div1(7.0,2.0)).toBe(3.5);
    });
    test('my_math.div1(8.0,2.0) should returns 4.0', () => {
        expect(my_math.div1(8.0,2.0)).toBe(4.0);
    });
    test('my_math.div1(9.0,2.0) should returns 4.5', () => {
        expect(my_math.div1(9.0,2.0)).toBe(4.5);
    });
    test('my_math.div1(10.0,2.0) should returns 5.0', () => {
        expect(my_math.div1(10.0,2.0)).toBe(5.0);
    });
    test('my_math.div1(11.0,2.0) should returns 5.5', () => {
        expect(my_math.div1(11.0,2.0)).toBe(5.5);
    });
    test('my_math.div1(12.0,2.0) should returns 6.0', () => {
        expect(my_math.div1(12.0,2.0)).toBe(6.0);
    });
    test('my_math.div1(13.0,2.0) should returns 6.5', () => {
        expect(my_math.div1(13.0,2.0)).toBe(6.5);
    });
    test('my_math.div1(14.0,2.0) should returns 7.0', () => {
        expect(my_math.div1(14.0,2.0)).toBe(7.0);
    });
    test('my_math.div1(15.0,2.0) should returns 7.5', () => {
        expect(my_math.div1(15.0,2.0)).toBe(7.5);
    });
    test('my_math.div1(16.0,2.0) should returns 8.0', () => {
        expect(my_math.div1(16.0,2.0)).toBe(8.0);
    });
    test('my_math.div1(17.0,2.0) should returns 8.5', () => {
        expect(my_math.div1(17.0,2.0)).toBe(8.5);
    });
    test('my_math.div1(18.0,2.0) should returns 9.0', () => {
        expect(my_math.div1(18.0,2.0)).toBe(9.0);
    });
    test('my_math.div1(19.0,2.0) should returns 9.5', () => {
        expect(my_math.div1(19.0,2.0)).toBe(9.5);
    });
    test('my_math.div1(20.0,2.0) should returns 10.0', () => {
        expect(my_math.div1(20.0,2.0)).toBe(10.0);
    });
    test('my_math.div1(21.0,2.0) should returns 10.5', () => {
        expect(my_math.div1(21.0,2.0)).toBe(10.5);
    });
    test('my_math.div1(22.0,2.0) should returns 11.0', () => {
        expect(my_math.div1(22.0,2.0)).toBe(11.0);
    });
    test('my_math.div1(23.0,2.0) should returns 11.5', () => {
        expect(my_math.div1(23.0,2.0)).toBe(11.5);
    });
    test('my_math.div1(24.0,2.0) should returns 12.0', () => {
        expect(my_math.div1(24.0,2.0)).toBe(12.0);
    });
    test('my_math.div1(25.0,2.0) should returns 12.5', () => {
        expect(my_math.div1(25.0,2.0)).toBe(12.5);
    });
    test('my_math.div1(26.0,2.0) should returns 13.0', () => {
        expect(my_math.div1(26.0,2.0)).toBe(13.0);
    });
    test('my_math.div1(27.0,2.0) should returns 13.5', () => {
        expect(my_math.div1(27.0,2.0)).toBe(13.5);
    });
    test('my_math.div1(28.0,2.0) should returns 14.0', () => {
        expect(my_math.div1(28.0,2.0)).toBe(14.0);
    });
    test('my_math.div1(29.0,2.0) should returns 14.5', () => {
        expect(my_math.div1(29.0,2.0)).toBe(14.5);
    });
    test('my_math.div1(30.0,2.0) should returns 15.0', () => {
        expect(my_math.div1(30.0,2.0)).toBe(15.0);
    });
    test('my_math.div1(31.0,2.0) should returns 15.5', () => {
        expect(my_math.div1(31.0,2.0)).toBe(15.5);
    });
    test('my_math.div1(32.0,2.0) should returns 16.0', () => {
        expect(my_math.div1(32.0,2.0)).toBe(16.0);
    });
    test('my_math.div1(33.0,2.0) should returns 16.5', () => {
        expect(my_math.div1(33.0,2.0)).toBe(16.5);
    });
    test('my_math.div1(34.0,2.0) should returns 17.0', () => {
        expect(my_math.div1(34.0,2.0)).toBe(17.0);
    });
    test('my_math.div1(35.0,2.0) should returns 17.5', () => {
        expect(my_math.div1(35.0,2.0)).toBe(17.5);
    });
    test('my_math.div1(36.0,2.0) should returns 18.0', () => {
        expect(my_math.div1(36.0,2.0)).toBe(18.0);
    });
    test('my_math.div1(37.0,2.0) should returns 18.5', () => {
        expect(my_math.div1(37.0,2.0)).toBe(18.5);
    });
    test('my_math.div1(38.0,2.0) should returns 19.0', () => {
        expect(my_math.div1(38.0,2.0)).toBe(19.0);
    });
    test('my_math.div1(39.0,2.0) should returns 19.5', () => {
        expect(my_math.div1(39.0,2.0)).toBe(19.5);
    });
    test('my_math.div1(40.0,2.0) should returns 20.0', () => {
        expect(my_math.div1(40.0,2.0)).toBe(20.0);
    });
    test('my_math.div1(41.0,2.0) should returns 20.5', () => {
        expect(my_math.div1(41.0,2.0)).toBe(20.5);
    });
    test('my_math.div1(42.0,2.0) should returns 21.0', () => {
        expect(my_math.div1(42.0,2.0)).toBe(21.0);
    });
    test('my_math.div1(43.0,2.0) should returns 21.5', () => {
        expect(my_math.div1(43.0,2.0)).toBe(21.5);
    });
    test('my_math.div1(44.0,2.0) should returns 22.0', () => {
        expect(my_math.div1(44.0,2.0)).toBe(22.0);
    });
    test('my_math.div1(45.0,2.0) should returns 22.5', () => {
        expect(my_math.div1(45.0,2.0)).toBe(22.5);
    });
    test('my_math.div1(46.0,2.0) should returns 23.0', () => {
        expect(my_math.div1(46.0,2.0)).toBe(23.0);
    });
    test('my_math.div1(47.0,2.0) should returns 23.5', () => {
        expect(my_math.div1(47.0,2.0)).toBe(23.5);
    });
    test('my_math.div1(48.0,2.0) should returns 24.0', () => {
        expect(my_math.div1(48.0,2.0)).toBe(24.0);
    });
    test('my_math.div1(49.0,2.0) should returns 24.5', () => {
        expect(my_math.div1(49.0,2.0)).toBe(24.5);
    });
    test('my_math.div1(50.0,2.0) should returns 25.0', () => {
        expect(my_math.div1(50.0,2.0)).toBe(25.0);
    });
    test('my_math.div1(51.0,2.0) should returns 25.5', () => {
        expect(my_math.div1(51.0,2.0)).toBe(25.5);
    });
    test('my_math.div1(52.0,2.0) should returns 26.0', () => {
        expect(my_math.div1(52.0,2.0)).toBe(26.0);
    });
    test('my_math.div1(53.0,2.0) should returns 26.5', () => {
        expect(my_math.div1(53.0,2.0)).toBe(26.5);
    });
    test('my_math.div1(54.0,2.0) should returns 27.0', () => {
        expect(my_math.div1(54.0,2.0)).toBe(27.0);
    });
    test('my_math.div1(55.0,2.0) should returns 27.5', () => {
        expect(my_math.div1(55.0,2.0)).toBe(27.5);
    });
    test('my_math.div1(56.0,2.0) should returns 28.0', () => {
        expect(my_math.div1(56.0,2.0)).toBe(28.0);
    });
    test('my_math.div1(57.0,2.0) should returns 28.5', () => {
        expect(my_math.div1(57.0,2.0)).toBe(28.5);
    });
    test('my_math.div1(58.0,2.0) should returns 29.0', () => {
        expect(my_math.div1(58.0,2.0)).toBe(29.0);
    });
    test('my_math.div1(59.0,2.0) should returns 29.5', () => {
        expect(my_math.div1(59.0,2.0)).toBe(29.5);
    });
    test('my_math.div1(60.0,2.0) should returns 30.0', () => {
        expect(my_math.div1(60.0,2.0)).toBe(30.0);
    });
    test('my_math.div1(61.0,2.0) should returns 30.5', () => {
        expect(my_math.div1(61.0,2.0)).toBe(30.5);
    });
    test('my_math.div1(62.0,2.0) should returns 31.0', () => {
        expect(my_math.div1(62.0,2.0)).toBe(31.0);
    });
    test('my_math.div1(63.0,2.0) should returns 31.5', () => {
        expect(my_math.div1(63.0,2.0)).toBe(31.5);
    });
    test('my_math.div1(64.0,2.0) should returns 32.0', () => {
        expect(my_math.div1(64.0,2.0)).toBe(32.0);
    });
    test('my_math.div1(65.0,2.0) should returns 32.5', () => {
        expect(my_math.div1(65.0,2.0)).toBe(32.5);
    });
    test('my_math.div1(66.0,2.0) should returns 33.0', () => {
        expect(my_math.div1(66.0,2.0)).toBe(33.0);
    });
    test('my_math.div1(67.0,2.0) should returns 33.5', () => {
        expect(my_math.div1(67.0,2.0)).toBe(33.5);
    });
    test('my_math.div1(68.0,2.0) should returns 34.0', () => {
        expect(my_math.div1(68.0,2.0)).toBe(34.0);
    });
    test('my_math.div1(69.0,2.0) should returns 34.5', () => {
        expect(my_math.div1(69.0,2.0)).toBe(34.5);
    });
    test('my_math.div1(70.0,2.0) should returns 35.0', () => {
        expect(my_math.div1(70.0,2.0)).toBe(35.0);
    });
    test('my_math.div1(71.0,2.0) should returns 35.5', () => {
        expect(my_math.div1(71.0,2.0)).toBe(35.5);
    });
    test('my_math.div1(72.0,2.0) should returns 36.0', () => {
        expect(my_math.div1(72.0,2.0)).toBe(36.0);
    });
    test('my_math.div1(73.0,2.0) should returns 36.5', () => {
        expect(my_math.div1(73.0,2.0)).toBe(36.5);
    });
    test('my_math.div1(74.0,2.0) should returns 37.0', () => {
        expect(my_math.div1(74.0,2.0)).toBe(37.0);
    });
    test('my_math.div1(75.0,2.0) should returns 37.5', () => {
        expect(my_math.div1(75.0,2.0)).toBe(37.5);
    });
    test('my_math.div1(76.0,2.0) should returns 38.0', () => {
        expect(my_math.div1(76.0,2.0)).toBe(38.0);
    });
    test('my_math.div1(77.0,2.0) should returns 38.5', () => {
        expect(my_math.div1(77.0,2.0)).toBe(38.5);
    });
    test('my_math.div1(78.0,2.0) should returns 39.0', () => {
        expect(my_math.div1(78.0,2.0)).toBe(39.0);
    });
    test('my_math.div1(79.0,2.0) should returns 39.5', () => {
        expect(my_math.div1(79.0,2.0)).toBe(39.5);
    });
    test('my_math.div1(80.0,2.0) should returns 40.0', () => {
        expect(my_math.div1(80.0,2.0)).toBe(40.0);
    });
    test('my_math.div1(81.0,2.0) should returns 40.5', () => {
        expect(my_math.div1(81.0,2.0)).toBe(40.5);
    });
    test('my_math.div1(82.0,2.0) should returns 41.0', () => {
        expect(my_math.div1(82.0,2.0)).toBe(41.0);
    });
    test('my_math.div1(83.0,2.0) should returns 41.5', () => {
        expect(my_math.div1(83.0,2.0)).toBe(41.5);
    });
    test('my_math.div1(84.0,2.0) should returns 42.0', () => {
        expect(my_math.div1(84.0,2.0)).toBe(42.0);
    });
    test('my_math.div1(85.0,2.0) should returns 42.5', () => {
        expect(my_math.div1(85.0,2.0)).toBe(42.5);
    });
    test('my_math.div1(86.0,2.0) should returns 43.0', () => {
        expect(my_math.div1(86.0,2.0)).toBe(43.0);
    });
    test('my_math.div1(87.0,2.0) should returns 43.5', () => {
        expect(my_math.div1(87.0,2.0)).toBe(43.5);
    });
    test('my_math.div1(88.0,2.0) should returns 44.0', () => {
        expect(my_math.div1(88.0,2.0)).toBe(44.0);
    });
    test('my_math.div1(89.0,2.0) should returns 44.5', () => {
        expect(my_math.div1(89.0,2.0)).toBe(44.5);
    });
    test('my_math.div1(90.0,2.0) should returns 45.0', () => {
        expect(my_math.div1(90.0,2.0)).toBe(45.0);
    });
    test('my_math.div1(91.0,2.0) should returns 45.5', () => {
        expect(my_math.div1(91.0,2.0)).toBe(45.5);
    });
    test('my_math.div1(92.0,2.0) should returns 46.0', () => {
        expect(my_math.div1(92.0,2.0)).toBe(46.0);
    });
    test('my_math.div1(93.0,2.0) should returns 46.5', () => {
        expect(my_math.div1(93.0,2.0)).toBe(46.5);
    });
    test('my_math.div1(94.0,2.0) should returns 47.0', () => {
        expect(my_math.div1(94.0,2.0)).toBe(47.0);
    });
    test('my_math.div1(95.0,2.0) should returns 47.5', () => {
        expect(my_math.div1(95.0,2.0)).toBe(47.5);
    });
    test('my_math.div1(96.0,2.0) should returns 48.0', () => {
        expect(my_math.div1(96.0,2.0)).toBe(48.0);
    });
    test('my_math.div1(97.0,2.0) should returns 48.5', () => {
        expect(my_math.div1(97.0,2.0)).toBe(48.5);
    });
    test('my_math.div1(98.0,2.0) should returns 49.0', () => {
        expect(my_math.div1(98.0,2.0)).toBe(49.0);
    });
    test('my_math.div1(99.0,2.0) should returns 49.5', () => {
        expect(my_math.div1(99.0,2.0)).toBe(49.5);
    });
    test('my_math.div1(100.0,2.0) should returns 50.0', () => {
        expect(my_math.div1(100.0,2.0)).toBe(50.0);
    });
});