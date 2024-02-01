const my_math = require('./my_math');

var t = my_math.inc(1);
my_math.div1(1,2);

for (let i = 0; i < 100; i++) {
  my_math.div1(1+i,2);
  console.log('test')
}

// division
my_math.div1(2,2);
my_math.div1(2,3);

var i = 0

while(i < 10) {
  my_math.inc(1);
  my_math.div1(1,2);
}


var i = 0

if(i > 6) {
  my_math.inc(1);
  my_math.div1(1,2);
}
else {
  my_math.div1(1,2);
}
