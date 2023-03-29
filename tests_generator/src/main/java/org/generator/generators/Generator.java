package org.generator.generators;


import org.generator.Trace;

import java.io.IOException;

public abstract class Generator {
    private final Trace trace;
    private final String lineSeparator;

    public Generator(Trace trace, String lineSeparator) {
        this.trace = trace;
        this.lineSeparator = lineSeparator;
    }

    abstract String createTests();

    abstract void writeTests(String path) throws IOException;

    public String getLineSeparator() {
        return lineSeparator;
    }

    public Trace getTrace() {
        return trace;
    }
}
