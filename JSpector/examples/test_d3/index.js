const d3 = require('d3');

// This example relies on the one provided in the d3-node library documentation
// https://github.com/d3-node/d3node-congress-map
const topojson = require('topojson')
const topo = require('./congress.json') // source: https://github.com/bradoyler/atlas-make/tree/master/us-states

const canvasModule = require('canvas');
const D3Node = require('d3-node');
const fs = require('fs');

// Instantiate the d3Node library.
const d3nOptions = {
  // Specify the d3 module we want to work with (in our case, the wrapped one).
  d3Module: d3,
  canvasModule: canvasModule
};
const d3n = new D3Node(d3nOptions);

// Build the map.
const width = 960
const height = 500
const projection = d3.geoAlbersUsa()
const path = d3.geoPath().projection(projection)
const svg = d3n.createSVG(width, height)

svg.selectAll('.region')
  .data(topojson.feature(topo, topo.objects.congress).features)
  .enter()
  .append('path')
  .attr('class', 'region')
  .attr('d', path)
  .style('fill', function (d) {
    if (d.properties.PARTY_AFF === 'Democrat') {
      return '#295899'
    } else {
      return '#b4362b'
    }
  })
  .style('stroke', '#aaa')
  .style('stroke-width', '0.6px')

// Save the svg file
fs.writeFileSync('congress.svg', d3n.svgString())

// other example (previous)
// const lib_name = 'd3-node';
// const D3Node = require('d3-node')
// const canvasModule = require('canvas');
// const fs = require('fs');
// const d3n = new D3Node({ canvasModule });
// let height = 900;
// let width = 900;
// const canvas = d3n.createCanvas(width, height);
// // const context = new JSpector(require('canvas'), lib_name, 'fillRect', __filename, 'SSE23-d3').get_library();
// const context = canvas.getContext('2d');
// for (var i=0;i < width; i++) {
//   for (var j=0;j < height; j++) {
//     let randomColor = "#000000".replace(/0/g,function(){return (~~(Math.random()*16)).toString(16);});
//     context.fillStyle = randomColor;
//     context.fillRect(i, j, 1, 1)
//   }
// }
// canvas.pngStream().pipe(fs.createWriteStream('output.png'));
