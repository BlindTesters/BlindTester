const ft = require('fourier-transform');
const decibels = require('decibels');

describe('blindtester-decibels.fromGain', () => {
    test('decibels.fromGain(8.712695685634506E-5) should returns -81.1961111425522', () => {
        expect(decibels.fromGain(8.712695685634506E-5)).toBe(-81.1961111425522);
    });
    test('decibels.fromGain(0.9742403447451903) should returns -0.22667545289055904', () => {
        expect(decibels.fromGain(0.9742403447451903)).toBe(-0.22667545289055904);
    });
});