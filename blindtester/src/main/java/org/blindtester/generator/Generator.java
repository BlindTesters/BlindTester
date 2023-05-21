package org.blindtester.generator;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.javatuples.Pair;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

/**
 * Abstract class that represent a test generator
 */
public abstract class Generator {
    /**
     * The trace of the execution of code
     */
    private final Trace trace;

    /**
     * The line separator
     */
    private final String lineSeparator;

    /**
     * Constructor that create a Generator
     *
     * @param trace         the trace that contains the function executions
     * @param lineSeparator the line separator
     */
    public Generator(Trace trace, String lineSeparator) {
        this.trace = trace;
        this.lineSeparator = lineSeparator;
    }

    /**
     * Abstract function to create tests
     *
     * @param functionName the name of the function
     * @param requires     the list of requirements (libraries, imports, ...)
     * @param calls        the list of function calls
     * @return the string that contains tests
     */
    public abstract String createTests(String functionName, List<Require> requires, List<Call> calls);

    /**
     * Write test to a file
     *
     * @param path     the path to the file
     * @param testType the type of test to execute
     * @throws Exception
     */
    public abstract void writeTests(String path, String testType) throws Exception;

    /**
     * Het the line separator that the generator uses
     *
     * @return the line separator
     */
    public String getLineSeparator() {
        return lineSeparator;
    }

    /**
     * Get the trace that the generator uses
     *
     * @return the trace
     */
    public Trace getTrace() {
        return trace;
    }

}
