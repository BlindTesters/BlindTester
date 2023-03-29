# SSE23-Project

This demonstrates the concept of wrapping a function in order to log its argument and return value. Define and export functions to test in a file. Provide the file path in first argument when executing `index.js`. The second argument will be an array of arrays containing parameters to provide to the functions to test (JSON-format).

### Execution

As stated in the previous section, you can provide a specific file and different inputs to test.

`node index.js <FILE_PATH> "[[arg1, arg2], [arg3, arg4]]"`

#### Example with output

`node index.js ./functions.js "[[1,2,3], [3,2,1], [50, 25, 0]]"`

    { function_name: 'diff', arguments: [ 1, 2, 3 ], result: -4 }
    { function_name: 'diff', arguments: [ 3, 2, 1 ], result: 0 }
    { function_name: 'diff', arguments: [ 50, 25, 0 ], result: 25 }
    {
    function_name: 'div',
    arguments: [ 1, 2, 3 ],
    result: 0.16666666666666666
    }
    { function_name: 'div', arguments: [ 3, 2, 1 ], result: 1.5 }
    { function_name: 'div', arguments: [ 50, 25, 0 ], result: Infinity }
    { function_name: 'mult', arguments: [ 1, 2, 3 ], result: 6 }
    { function_name: 'mult', arguments: [ 3, 2, 1 ], result: 6 }
    { function_name: 'mult', arguments: [ 50, 25, 0 ], result: 0 }
    { function_name: 'sum', arguments: [ 1, 2, 3 ], result: 6 }
    { function_name: 'sum', arguments: [ 3, 2, 1 ], result: 6 }
    { function_name: 'sum', arguments: [ 50, 25, 0 ], result: 75 }
