const Tracer = require('njstrace/lib/tracer');

class CustomTracer extends Tracer {
  constructor(config) {
    super(config);
    // Add callId to be able to map the result.
    this.onEntryArgs['callId'] = null;
    this.onExitArgs['callId'] = null;
  }

  // override onEntry to have more control over the arguments
  onEntry(args) {
    // The following lines are mostly copied from njstrace/lib/tracer.js
    var stackFrame = args.name + '@' + args.file + '::' + args.line;
    var sid = this.pushFrame(stackFrame);

    // We are recycling the same args object again and again to ease on GC
    // This means that the formatters should NOT keep a reference to this object in some async callback as it will change
    this.onEntryArgs.name = args.name;
    this.onEntryArgs.file = args.file;
    this.onEntryArgs.line = args.line;
    this.onEntryArgs.args = args.args;
    this.onEntryArgs.stack = this.stack;
    // Add callId to be able to map the result.
    this.onEntryArgs.callId = sid;

    for (var i = 0; i < this.formatters.length; ++i) {
      this.formatters[i].onEntry(this.onEntryArgs);
    }

    // This would be the args.entryData on the onExit function
    return { name: args.name, file: args.file, fnLine: args.line, ts: Date.now(), stackId: sid };
  }

  // override onExit to have more control over the arguments
  onExit(args) {
    let callId = args.entryData.stackId;

    // The following lines are mostly copied from njstrace/lib/tracer.js
    var ts = Date.now() - args.entryData.ts;
    this.popFrame(args.entryData.stackId);

    // We are recycling the same args object again and again to ease on GC
    // This means that the formatters should NOT keep a reference to this object in some async callback as it will change
    this.onExitArgs.name = args.entryData.name;
    this.onExitArgs.file = args.entryData.file;
    this.onExitArgs.line = args.entryData.fnLine;
    this.onExitArgs.retLine = args.line;
    this.onExitArgs.span = ts;
    this.onExitArgs.stack = this.stack;
    this.onExitArgs.exception = args.exception;
    this.onExitArgs.returnValue = args.returnValue;
    // Add callId to be able to map the result.
    this.onExitArgs.callId = callId;

    for (var i = 0; i < this.formatters.length; ++i) {
      this.formatters[i].onExit(this.onExitArgs);
    }
  }
}

module.exports = CustomTracer;
