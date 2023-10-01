const db = require('decibels');
var ft = require('fourier-transform');

var frequency = 440;
var size = 2 ** 15;
var sampleRate = 44100;
var waveform = new Float32Array(size);
for (var i = 0; i < size; i++) {
  waveform[i] = Math.sin(frequency * Math.PI * 2 * (i / sampleRate));
}

//get normalized magnitudes for frequencies from 0 to 22050 with interval 44100/1024 ≈ 43Hz
var spectrum = ft(waveform);

//convert to decibels
var decibels = spectrum.map((value) => db.fromGain(value))

console.log(decibels)
