# JSpector - Get trace from a NodeJS app execution

<p align="center">
    <img 
        src="https://creazilla-store.fra1.digitaloceanspaces.com/emojis/48141/detective-emoji-clipart-md.png" 
        width="100" 
        height="100">
</p>

`JSpector` was written as part of the `BlindTester` which was a project carried out as part of the BeNeFri Joint Master of Computer Science during the Software Engineering Seminar 2023 at University of Bern.

The goal of this tool is to intercept every call to a given function to write a trace of the execution in a file (usually named `trace.json`). This file will then be used by the `BlindTester` application.

## 1. Installation

In your terminal, go to the tool's root and install all the required dependencies with the following command : 

``` shell
npm install
```

## 2. Quickstart

`JSpector` works as a wrapper that will transparently replace the desired function in order to keep a track of every input and output of the function. If one wants to use the library in their project, it will require to create a file (usually called `instrument.js`) to instrument the application. An example of such a file is given below. This file can be adapted to one needs, according to their use case.

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

In order to actually run the injection, you will also need to use the `require` option of the node executable. Assuming the content of the file above is saved in a file called `instrument.js` and that your application is run via `node index.js`, you will have to add `-r ./instrument.js` to the command, in order to explicitly tell node to use this additional requirement.

```sh
$ node -r ./instrument.js index.js
```

Doing so gives a higher degree of flexibility since you can still execute your application without instrumenting anything simply by simply removing the `-r` option in the command.

*Please note that the trace contains the path of the project. Please restart JSpector or edit the trace.json file if your project has moved to another directory.*

## License

All rights reserved.

Image is under CCA 4.0 and created by *JoyPixels* from [creazilla.com](https://creazilla.com) :

- [Detective](https://creazilla.com/nodes/48141-detective-emoji-clipart)
