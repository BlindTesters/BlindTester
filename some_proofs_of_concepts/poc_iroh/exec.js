const my_math = require('./my_math');
const my_vectors = require('./my_vectors');
const my_hello = require('./my_hello');
const { sayHello } = require('./my_hello');

// sum of 3 numbers
my_math.sum(5,6,7);

// division a/b
my_math.div1(1,2);

// division a/(b+1)
my_math.div2(1,2);

// add 2d vectors
v1 = new my_vectors.Vector(1,2);
v2 = new my_vectors.Vector(1,2)
my_vectors.addVec(v1, v2)

// add 2d vectors
v3 = new my_vectors.Vector(20,20);
v4 = new my_vectors.Vector(10,20)
my_vectors.addVec(v3, v4)


sayHello("Test")
