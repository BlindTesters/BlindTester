// Inject a wrapper around the function in the class we want to inspect.
const Injector = require('../injector/injector.js');
const lib_name = 'd3-node';
const D3Node = new Injector(require(lib_name), lib_name, 'createSVG', __filename, 'SSE23-d3').get_library();

D3Node().createSVG(10,20).append('g');
D3Node().svgString()
