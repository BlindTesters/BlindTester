# BlindTester

<p align="center">
    <img 
    src="https://creazilla-store.fra1.digitaloceanspaces.com/emojis/46374/sunglasses-emoji-clipart-md.png" 
    width="250" 
    height="250">
</p>

This repo contains the code that was written during a project carried out as part of the BeNeFri Joint Master of Computer Science during the Software Engineering Seminar 2023 at University of Bern.

The goal of this project is to automatically generate unit tests for a specific function from traces files containing information about the runtime execution of a NodeJS program. The project had as a prerequisite that each function call shoud be considered a black box. This approach allows to easily generate a wide range of input and output for a given function, which could make easier a lot of tasks such as debugging, testing after upgrades, etc.

In fact, this project consists of two parts:

1. A small Javascript (JS) library called `JSpector`, which is used to get runtime execution trace from NodeJS runtime execution
2. A Java program called `BlindTester` that will use `JSpector`'s output to generate Jest unit tests for JS programs

The trace file contains all inputs and outputs for a chosen function. `BlindTester` will generate a test for each distinct call.

## 1. Overview

To be able to  capture NodeJS runtime execution, we developed a small tool that needs to be injected in your main JS file running in NodeJS.

At the end of the program execution, `JSpector` will output a `trace.json` file in your project's root directory.

Thanks to this JSON file, `BlindTester` will be able to generate unit tests based on the runtime execution trace.

- Step 1 : Inject the library [JSpector](JSpector/) in your NodeJS application. This will allow to capture all the inputs and outputs of a specific function.

- Step 2 : Execute your NodeJS application normally and let `JSpector` capture all function calls and generate a JSON file containing a trace and an Abstract Syntax Tree (AST) for each call of the analysed function.

- Step 3 : Provide `Blindtester` with the generated JSON file. `BlindTester` currently operates on two levels:
    1. It can generate several unit tests based on the runtime execution
    2. It can generate a report based on the trace

- Step 4 : If `BlindTester` was used to generate unit tests, it is possible to execute those. One should manually run the newly created tests. All the tests should pass (best case scenario). This methodology can also be used to perform regression tests. This means that the tests - generated from an actual program execution - can be used to verify that an application still works as intended after upgrades. For example, if some tests have been generated with version 1.0 of an application, theses tests run against version 1.1 of an application should still pass. If they don't, a manual inspection might be needed: the failures might be normal if the program has been heavily modified but this could also mean that the behaviour changed unintentionally.

## 2. Installation

### 2.1 External dependencies

- Install latest version of [NodeJS](https://nodejs.org/)

- Install [Open JDK 22](https://jdk.java.net/22/) or newer

- Install latest version of [Maven](https://maven.apache.org/)

#### 2.1.1 Data science dependencies

Our Java application (`BlindTester`) currently executes a python script to perform some data science tasks. Basically, the idea is to compute KMeans clusters in order to find similar application calls. You can find more information about this below in this file.

In order to use this feature, it is mandatory to install a minimal setup to make sure that `BlindTester` will be able to execute python scripts. We are aware that this situation is not ideal, but due to time constraints, we chose to keep this solution at the end of the Seminar. Therefore, we may implement a version of this in a fully Java solution at a later time.

However, if you choose to use this KMeans feature, please ensure that `distutils` is installed on your system if you are running python 3.12 or newer on your system. If your python version is older, you don't need to install `distutils`.

  - With pip
    - ```$ sh pip install setuptools ```
  - With pip3
    - ```$ pip3 install setuptools```
  - With brew on Mac OSX
    - ```$ brew install python-setuptools```

If `distutils` is installed (or you python version don't need this package), we recommend to use a [virtual environment](https://docs.python.org/3/library/venv.html) to install all the required python dependancies rather thant installing those system-wide. To use the virtual environment feature, please use the following commands from you terminal, provided that the current directory is the project's root:

``` sh
$ cd kmeans
$ python3 -m venv venv
$ source venv/bin/activate
$ pip install -r requirements.txt
```

### 2.2 Compile the test generator BlindTester

In a terminal, go to the project's directory and run the following command:

``` sh
$ mvn clean compile assembly:single
```

### 2.3 Execute JSpector to extract a trace file

Please use [JSpector](JSpector) to generate a trace.json file in your project to generate tests. 

One needs to follow the [JSpector README](JSpector) to install all the required dependencies and make sure `JSpector` is accessible by the project you want to create tests.

## 3. Generate a report and tests from an execution trace file
Please have a look at the [JSpector README](JSpector) before proceed reading the next sections.

As stated above, `BlindTester` operates in two modes: report generation or unit test generation. This section will go deeper in how these work.

### 3.1 Report generation (Optional)

Use the following command to generate a report based on the trace file :

``` sh
java -jar path_to_jar/blindtester.jar analyse [TRACE_PATH]
```

This will generate a PDF report on the analysis that may help you to decide which set of tests to generate.

### 3.2 Unit tests generation

Use the following command to generate unit tests based on the trace file :

``` sh
$ java -jar testbuilder.jar generate [GENERATOR] [TEST_TYPE] [TRACE_PATH]
```

Where
- `GENERATOR` is the name of the generator among the following ones:
    - `jest` : [Jest](https://jestjs.io/) is Test framework for JavaScript and TypeScript
- `TEST_TYPE` is one option among the following ones:
    - `all`     : Generate a unit test for every single call in the trace
    - `distinct`: Generate unit tests only for distinct calls
    - `minimal` : Generate unit tests only for the minimal set of calls
    - `kmeans`  : Generate one unit test for every detected cluster (via K-means method). This needs the use of python as stated in section [Data science dependencies](#211-data-science-dependencies).

*PLEASE NOTE: There is currently only a generator for Jest unit test  that is implemented in BlindTester. The list of compatible unit test frameworks might increase in the future.*

## 3.3 Execute the automatically generated tests with Jest

In a terminal, go to the project's directory and run the tests using Jest. Please make sure that Jest is intalled, and if not, install it.

``` sh
# If jest is not installed please install it
$ npm install jest

# run tests in the current directory
$ ./node_modules/jest/bin/jest.js
```

## 4. Examples

All examples are available in the [examples directory](examples)

For all tests below we assume that you are in the terminal and that the current directory is the root directory of the `BlindTester` project.

Before going any further, please make sure that you've installed all of `JSpector`'s dependencies : [JSpector README](JSpector).

### 4.1 Crypto - Generate tests for every single runtime execution call
This example focuses on the function `pbkdf2Sync` of the `crypto` library. As explained in [JSpector README](JSpector), the `instrument.js` file contains the instrumentation parameters and is loaded as an additional library when the NodeJS program starts.

#### 4.1.1 Generate a trace

```shell
cd examples/crypto_hash
npm install
node -r ./instrument.js index.js
cd ../../ #return to root directory
```

The image below shows the output of the program, showing that `JSpector` has been correctly loaded and was able to trace all the calls to `pbkdf2Sync`.

<p align="center">
    <img
    src="./docs/images/crypto_trace_gen.png"
    >
</p>

#### 4.1.2 Create tests from the trace

Now that the trace.json file has been written, it is possible to generate unit tests with the following parameters:

- `jest`: the target unit test framework
- `all`: the type of tests to generate (here one test per function call)
- `examples/crypto_hash/trace.json`: the path to the `trace.json` file.

```shell
java -jar target/blindtester-1.0-SNAPSHOT-jar-with-dependencies.jar generate jest all examples/crypto_hash/trace.json
```

<p align="center">
    <img
    src="./docs/images/crypto_tests.png"
    >
</p>

#### 4.1.3 Execute the tests

After the tests generation, it is possible to execute the unit test using Jest. Please make sure that Jest is installed, as explained in [Jest section](#33-execute-the-automatically-generated-tests-with-jest).

```shell
cd examples/crypto_hash
./node_modules/jest/bin/jest.js
```

### 4.2 Fourier - Generate a report

In this example, we want to generate a report about the calls to the function `fromGain` from the `decibels` library. This function is used alognside with the `fourier-transform` library in the `index.js` file.

#### 4.2.1 Generate a trace

```shell
cd examples/fourier_analysis
npm install
node -r ./instrument.js index.js  
cd ../../ #return to root directory
```

#### 4.2.2 Generate the report

Now that the trace.json file has been written, it is possible to generate a PDF report with the following parameters:

- `generate`: generate a report
- `analyse`: analyse all the calls of the trace
- `examples/fourier_analysis/trace.json`: the path to the `trace.json` file.

```shell
java -jar target/blindtester-1.0-SNAPSHOT-jar-with-dependencies.jar generate analyse examples/fourier_analysis/trace.json
```

You will find the generated report in the project directory.

### 4.3 Handle side effects by keeping all detected calls

The goal is to check that functions with side effects are handled correctly.

<p align="center">
    <img 
    src="./docs/images/side_code.png" 
    >
</p>

#### 4.3.1 Generate a trace

```shell
cd examples/side_effect
npm install
node -r ./instrument.js index.js  
cd ../../ #return to root directory
```

#### 4.3.2 Create tests from the trace

Now that the trace.json file has been written, it is possible to generate unit tests with the following parameters:

- `jest`: the target unit test framework
- `minimal`: the type of tests to generate (here the minimal set of calls to the function)
- `examples/side_effect/trace.json`: the path to the `trace.json` file.

```shell
java -jar target/blindtester-1.0-SNAPSHOT-jar-with-dependencies.jar generate jest minimal examples/side_effect/trace.json
```

#### 4.3.3 Execute the tests

After the tests generation, it is possible to execute the unit test using Jest. Please make sure that Jest is installed, as explained in [Jest section](#33-execute-the-automatically-generated-tests-with-jest).

```shell
cd examples/side_effect
./node_modules/jest/bin/jest.js
```

### 4.4 MathJS - Detect implementation changes for a function over time

Function to test : `mod` from mathjs library.

#### 4.4.1 Generate a trace

```shell
cd examples/diff_mathjs
npm install
node -r ./instrument.js index.js  
cd ../../ #return to root directory
```

#### 4.4.2 Create tests from the trace

Now that the trace.json file has been written, it is possible to generate unit tests with the following parameters:

- `jest`: the target unit test framework
- `minimal`: the type of tests to generate (here one for every call to the function)
- `examples/diff_mathjs/trace.json`: the path to the `trace.json` file.

```shell
java -jar target/blindtester-1.0-SNAPSHOT-jar-with-dependencies.jar generate jest all examples/diff_mathjs/trace.json
```

#### 4.4.3 Execute the tests

After the tests generation, it is possible to execute the unit test using Jest. Please make sure that Jest is installed, as explained in [Jest section](#33-execute-the-automatically-generated-tests-with-jest).

```shell
cd examples/diff_mathjs
./node_modules/jest/bin/jest.js
```

Confirm that they all pass.

#### 4.4.4 Change mathjs version in package.json

For regression testing, please edit `examples/diff_mathjs/package.json` and change `mathjs`'s version from 11.8.0 to 12.4.1 (line 8).

#### 4.4.5 Update the dependency

In order to install the newer version, please run the following command

```shell
npm install
```

#### 4.4.6 Execute the tests again with the new version

After the dependency update, it is possible to execute the unit tests again.

```shell
./node_modules/jest/bin/jest.js
```

You can now see that the tests are failing. Some inverstigation will show that this function's implementation has changed, causing it to return different values. This allows to either

1. Detect unwanted changes
2. Assert that a bug is correctly fixed

## License

All rights reserved.

Image is under CCA 4.0 and created by *JoyPixels* from [creazilla.com](https://creazilla.com) :

- [Glasses](https://creazilla.com/nodes/46374-sunglasses-emoji-clipart)
