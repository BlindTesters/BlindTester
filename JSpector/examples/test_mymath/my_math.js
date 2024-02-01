function sum(a, b, c){
  return a+b+c
}

function diff (a, b, c) {
  return a-b-c;
}

function doubler(n) {
  return 2*n;
}

function div1(a,b){
  return a/b;
}

function div2(a,b){
  return a/(b+1);
}

function inc(x, i=1) {
  return x+i;
}

class Vector {
    constructor(x, y) {
      this.x = x;
      this.y = y
    }
}

function addVec(v1, v2) {
  return new Vector(v1.x + v2.x, v1.y + v2.y);
}

module.exports = { Vector, addVec, inc, sum, diff, div1, div2, doubler }
