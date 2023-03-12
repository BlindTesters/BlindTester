const GLOBAL_TRACE = {}

const store_result = function (func, arguments, output) {
  // Initialize global trace for the particular function.
  if(GLOBAL_TRACE.hasOwnProperty(func.name) === false) {
    GLOBAL_TRACE[func.name] = [];
  }
  // Store the function's output according to the passed arguments.
  GLOBAL_TRACE[func.name].push({'arguments': arguments, 'output': output});
}

// Initial function to test.
var sum = function(a, b, c) {
  return a+b+c;
}

// Wrap function.
var _sum = sum;

// Wrapped function to test the initial function.
var sum = function(a, b, c) {
  let res = _sum(a, b, c);
  store_result(arguments.callee, arguments, res);
  return res;
}
