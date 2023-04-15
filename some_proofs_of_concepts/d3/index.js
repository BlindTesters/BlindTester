// Inject a wrapper around the function in the class we want to inspect.
const Injector = require('../injector/injector.js');
const lib_name = 'd3-node';
const D3Node = new Injector(require(lib_name), lib_name, 'select', __filename, 'SSE23-d3').get_library();

// Run an example from the doc.
const options = { selector: '#chart', container: '<div id="container"><div id="chart"></div></div>' }
const d3n = new D3Node(options) // initializes D3 with container element
const d3 = d3n.d3

d3.select(d3n.document.querySelector('#chart')).append('span');
d3n.html();
console.log(d3n.chartHTML());
