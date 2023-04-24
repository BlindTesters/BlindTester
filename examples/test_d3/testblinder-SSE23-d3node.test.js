const topojson = require('topojson');
const topo = require('./congress.json');
const canvasModule = require('canvas');
const D3Node = require('d3-node');
const fs = require('fs');
const d3 = require('d3');

describe('blindtester-SSE23-d3node', () => {
    test('d3.selection.attr("xmlns","http://www.w3.org/2000/svg") should returns {"_groups":[[{}]],"_parents":[null]}', () => {
        expect(d3.selection.attr("xmlns","http://www.w3.org/2000/svg")).toMatchObject({"_groups":[[{}]],"_parents":[null]});
    });
    test('d3.selection.attr("width",960.0) should returns {"_groups":[[{}]],"_parents":[null]}', () => {
        expect(d3.selection.attr("width",960.0)).toMatchObject({"_groups":[[{}]],"_parents":[null]});
    });
    test('d3.selection.attr("height",500.0) should returns {"_groups":[[{}]],"_parents":[null]}', () => {
        expect(d3.selection.attr("height",500.0)).toMatchObject({"_groups":[[{}]],"_parents":[null]});
    });
});
