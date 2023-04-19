# BlindTester

<img 
    src="https://creazilla-store.fra1.digitaloceanspaces.com/emojis/46374/sunglasses-emoji-clipart-md.png" 
    width="100" 
    height="100"
    style="display: block; margin: 0 auto"
    >

Project for the Software Engineering Seminar 2023 at UniBE

The goal of this project is to generate automatically tests from runtime execution for a specific function.

## Dependencies

- Install [NodeJS](https://nodejs.org/)

- Install [Open JDK 19](https://jdk.java.net/19/)

- Install [Maven](https://maven.apache.org/)


## Get trace from execution with Inspectors

<img 
    src="https://creazilla-store.fra1.digitaloceanspaces.com/emojis/48141/detective-emoji-clipart-md.png" 
    width="100" 
    height="100"
    style="display: block; margin: 0 auto">


### JSpector - Get trace from a NodeJS app 

Inject the following snippet in your application to get a trace of the desired function to analyze :

``` javascript
const JSpector = require('../../JSpector/jspector');

// replace LIB_NAME with the name of your library that contains the function to test
const lib_name = 'LIB_NAME';

// replace FUNCTION_NAME with the name of your function to test
const crypto = new JSpector(require(lib_name), lib_name, 'FUNCTION_NAME', __filename, 'SSE23-crypto').get_library();
```

## BlindTester

### Compile BlindTester

Go to the project's directory and : 

``` sh
$ mvn clean compile assembly:single
```

### Generate some tests for Jest

``` sh
$ java -jar path_to_jar/blindtester-1.0-SNAPSHOT-jar-with-dependencies.jar path_to_trace/trace.json
```

### Execute tests with Jest

Go to your project directory and : 

``` sh
# If jest is not installed please install it
$ npm install jest

# run tests in the current directory
$ ./node_modules/jest/bin/jest.js
```

## Example

Coming soon...

## License

All rights reserved.

Images are both created by JoyPixels from creazilla :

- [Glasses](https://creazilla.com/nodes/46374-sunglasses-emoji-clipart)

- [Detective](https://creazilla.com/nodes/48141-detective-emoji-clipart)
