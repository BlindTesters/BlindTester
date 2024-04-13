var a = 0;

// increment a global variable and returns the value of it
function inc(){
    a += 1;
    return a;
}

exports.inc = inc;
