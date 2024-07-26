# JSpector - Get trace from a NodeJS app execution

<p align="center">
    <img 
        src="https://creazilla-store.fra1.digitaloceanspaces.com/emojis/48141/detective-emoji-clipart-md.png" 
        width="100" 
        height="100">
</p>

Project for the Software Engineering Seminar 2023 at University of Bern for the BeNeFri Joint Master of Computer Science.

The goal of this project is to generate automatically tests for a specific function from runtime execution.

## 1. Installation

Install all dependencies for JSpector with the following command : 

``` shell
npm install
```

## 2. Quickstart

If you do want to use the libary in another project, you will need to create a file in the directory of your NodeJS project where you will specify some configuration that will wrap the targeted function and start the injection. You can copy-paste the following configuration and adapt it to your use case:

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

In order to actually use the injection, you will also need to add a requirements to the node command used to run the application. Assuming the content of the file above is saved in a file called `instrument.js` and that your application is run via `index.js`, you will have to add `-r ./instrument.js` to the command, in order to explicitly tell node to use this additional requirement.

```sh
$ node -r ./instrument.js index.js
```

Doing so gives a higher degree of flexibility since you can still execute your application without instrumenting anything simply by removing the `-r` option in the command.

*Please note that the trace contains the path of the project. Please restart JSpector or edit the trace.json file if your project changed path*

## License

All rights reserved.

Image is under CCA 4.0 and created by *JoyPixels* from [creazilla.com](https://creazilla.com) :

- [Detective](https://creazilla.com/nodes/48141-detective-emoji-clipart)
