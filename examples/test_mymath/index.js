const my_math = require('./my_math');

my_math.inc(1);
my_math.inc(1);
my_math.inc(1, 2);
my_math.inc(1, 1);
my_math.inc(1);

// sum of 3 numbers
my_math.sum(5,6,7);

// division a/b
my_math.div1(5,5);
my_math.div1(5,5);
my_math.div1(1,4);
my_math.div1(1,2);
my_math.div1(1,2);

for (let i = 0; i < 100; i++) {
  my_math.div1(1+i,2);
}

// division a/(b+1)
my_math.div2(1,2);

// add 2d vectors
v1 = new my_math.Vector(1,2);
v2 = new my_math.Vector(1,2);
my_math.addVec(v1, v2);

// add 2d vectors
v3 = new my_math.Vector(20,20);
v4 = new my_math.Vector(10,20);
my_math.addVec(v3, v4);
