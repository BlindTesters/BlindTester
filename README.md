# BlindTester

<img 
    src="https://creazilla-store.fra1.digitaloceanspaces.com/emojis/46374/sunglasses-emoji-clipart-md.png" 
    width="100" 
    height="100"
    style="display: block; margin: 0 auto">

Project for the Software Engineering Seminar 2023 at UniBE

The goal of this project is to generate automatically tests from runtime execution for a specific function.

## Dependencies

- Install [NodeJS](https://nodejs.org/)

- Install [Open JDK 19](https://jdk.java.net/19/)

- Install [Maven](https://maven.apache.org/)

## Compile BlindTester

``` sh
mvn clean compile assembly:single
```

## Get trace from NodeJS app execution with JSpector

<img 
    src="https://creazilla-store.fra1.digitaloceanspaces.com/emojis/48141/detective-emoji-clipart-md.png" 
    width="100" 
    height="100"
    style="display: block; margin: 0 auto">

``` javascript
const JSpector = require('../../JSpector/jspector');

const lib_name = 'LIB';

// replace FUNCTION with the name of your function
const crypto = new JSpector(require(lib_name), lib_name, 'FUNCTION', __filename, 'SSE23-crypto').get_library();
```

## Generate some tests with BlindTester

``` sh
java -jar path_to_jar/blindtester-1.0-SNAPSHOT-jar-with-dependencies.jar
```
