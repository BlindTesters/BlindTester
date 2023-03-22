function sum(a, b, c){
  return a+b+c
}

function div1(a,b){
  return a/b;
}

function div2(a,b){
  return a/(b+1);
}

class Vector {
  constructor(x, y) {
    this.x = x;
    this.y= y
  }
}

function addVec(v1, v2){
  return new Vector(v1.x+v2.x, v1.y+v2.y);
}

module.exports = { Vector, sum, div1, div2, addVec };
