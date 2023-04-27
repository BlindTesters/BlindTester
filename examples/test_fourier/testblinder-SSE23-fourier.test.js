const ft = require('fourier-transform');
const decibels = require('decibels');

describe('blindtester-SSE23-fourier', () => {
    test('decibels.fromGain(1.557345748715725E-4) should returns -76.15151327792287', () => {
        expect(decibels.fromGain(1.557345748715725E-4)).toBe(-76.15151327792287);
    });
});