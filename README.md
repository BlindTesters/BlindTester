# BlindTester

<p align="center">
    <img 
    src="https://creazilla-store.fra1.digitaloceanspaces.com/emojis/46374/sunglasses-emoji-clipart-md.png" 
    width="250" 
    height="250">
</p>

Project for the Software Engineering Seminar 2023 at University of Bern for the BeNeFri Joint Master of Computer Science.

The goal of this project is to generate automatically tests for a specific function from runtime execution.

## Dependencies

- Install [NodeJS](https://nodejs.org/)

- Install [Open JDK 19](https://jdk.java.net/19/)

- Install [Maven](https://maven.apache.org/)

## Get trace from execution with Inspectors

<p align="center">
    <img 
        src="https://creazilla-store.fra1.digitaloceanspaces.com/emojis/48141/detective-emoji-clipart-md.png" 
        width="100" 
        height="100">
</p>

### JSpector - Get trace from a NodeJS app execution

You will need to create a file in which you will specify some configuration that will be used to wrap the targeted function and start the injection. You can copy-paste the following configuration and adapt it to your use case:

``` javascript
// Inject a wrapper around the function in the class we want to inspect.
const JSpector = require('../../JSpector/jspector');  // the path to JSpector library.

const jspector = new JSpector(
  'decibels',  // the library name as you would require it (in this case the require would be "require('decibels')")
  'fromGain',  // the targeted function that will be wrapped
  'SSE23-fourier'  // the project name which will be used in the trace file.
);

// Start the actual injection.
jspector.start();
```

In order to actually use the injection, you will also need to add a requirements to the node command used to run the application. Assuming the content of the file above is saved in a file called `instrument.js` and that your application is run via `index.js`, you will have to add `-r ./instrument.js` to the command, in order to explicitely tell node to use this additional requirement.

```sh
$ node -r ./instrument.js index.js  
```

Doing so gives a higher degree of flexibility since you can still execute your application without instrumenting anything simply by removing the `-r` option in the command.

*Please note that the trace contains the path of the project. Please restart JSpector or edit the trace.json file if your project changed path*

## BlindTester

<p align="center">
    <img 
    src="https://creazilla-store.fra1.digitaloceanspaces.com/emojis/46374/sunglasses-emoji-clipart-md.png" 
    width="100" 
    height="100">
</p>

Each function call is considered as a black box. The trace contains all inputs and output for a specific function. BlindTester will generate a test for each distinct call.

### Compile BlindTester

Go to the project's directory and : 

``` sh
$ mvn clean compile assembly:single
```

#### KMeans specific configuration
We currently need a python installation to compute the KMeans cluster. If you want to use this feature, please make sure that you have a valid python installation on your computer. Then, create a python virtual environment in the kmeans folder and install all the dependencies in the virtual env.

``` sh
$ cd kmeans
$ python3 -m venv venv
$ source venv/bin/activate
$ pip install -r requirements.txt
```

You can now use the KMeans option.

NOTE: This is a quick solution to be able to provide some clustering. We might work on a Java implementation if we have enough time by the end of the semester. This was easier for us to do so in a first step for a proof-of-concept.

### Usage 

#### Report generation

``` sh
java -jar path_to_jar/blindtester.jar analyse [TRACE_PATH]
```

#### Unit tests generation

``` sh
$ java -jar testbuilder.jar generate [GENERATOR] [TEST_TYPE] [TRACE_PATH]
```

Where `GENERATOR` is the name of the generator that target a test system and `TEST_TYPE` one option among the following:

- `all` : Generate a test for all calls in the trace
- `distinct`: Generate tests only for distinct calls
- `minimal`: Generate tests only for the minimal set of calls
- `kmeans`: Generate only one test for detected cluster via KMeans method

*At this time, only a generator for Jest is implemented in BlindTester*

##### Generate all tests from a trace for Jest

``` sh
$ java -jar path_to_jar/blindtester.jar jest all path_to_trace/trace.json
```

##### Execute tests with Jest

Go to your project directory and : 

``` sh
# If jest is not installed please install it
$ npm install jest

# run tests in the current directory
$ ./node_modules/jest/bin/jest.js
```

## Examples

All examples are available in the [examples directory](examples/)

### Fourier

fromGain function from decibels library used by fourier-transform

Add JSpector

<p align="center">
    <img 
    src="./docs/images/fourier_jspector.png">
</p>

Generate a report

<p align="center">
    <img 
    src="./docs/images/fourier_report.png">
</p>

Generated unit tests

<p align="center">
    <img 
    src="./docs/images/fourier_tests.png">
</p>

Test execution with jest

<p align="center">
    <img 
    src="./docs/images/fourier_jest.png">
</p>

### Crypto

pbkdf2Sync function from Crypto module of NodeJS

101 calls to hash a word of which two are duplicates.

Generated tests for a distinct set of values

<p align="center">
    <img 
    src="./docs/images/crypto_generation.png">
</p>

Start Jest on automatically generated tests

<p align="center">
    <img 
    src="./docs/images/crypto_jest.png">
</p>

## License

All rights reserved.

Images are both under CCA 4.0 and created by *JoyPixels* from [creazilla.com](https://creazilla.com) :

- [Glasses](https://creazilla.com/nodes/46374-sunglasses-emoji-clipart)

- [Detective](https://creazilla.com/nodes/48141-detective-emoji-clipart)
