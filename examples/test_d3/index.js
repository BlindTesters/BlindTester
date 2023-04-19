// Inject a wrapper around the function in the class we want to inspect.
const JSpector = require('../../JSpector/jspector');

const canvasModule = require('canvas');
const fs = require('fs');

const lib_name = 'd3-node';
 const D3Node = require('d3-node')
//const D3Node = new Injector(require(lib_name), lib_name, 'D3Node.', __filename, 'SSE23-d3').get_library();

const d3n = new D3Node({ canvasModule });

let height = 900;
let width = 500;

const canvas = d3n.createCanvas(width, height);


const context = new JSpector(require('canvas'), lib_name, 'fillRect', __filename, 'SSE23-d3').get_library();

for (var i=0;i < width; i++) {
  for (var j=0;j < height; j++) {
    let randomColor = "#000000".replace(/0/g,function(){return (~~(Math.random()*16)).toString(16);});
    context.fillStyle = randomColor;
    context.fillRect(i, j, 1, 1)
  }
}

canvas.pngStream().pipe(fs.createWriteStream('output.png'));
