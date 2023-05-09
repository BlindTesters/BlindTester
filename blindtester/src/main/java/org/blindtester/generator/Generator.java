package org.blindtester.generator;

import org.javatuples.Pair;

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

    /**
     * Create a report in console about the number of calls in the trace
     */
    public void makeReport() {
        Trace t = getTrace();

        System.out.println("***************************************************");
        System.out.println("Report for project : " + t.getProjectName());
        System.out.println("- " + t.getRequires().size() + " libraries import");

        for (ExecutedFunction f : t.getExecutedFunctions()) {
            // get distinct calls
            Pair<Boolean, List<Call>> resultDistinct = f.getDistinctCalls();
            List<Call> distinctCalls = resultDistinct.getValue1();
            List<Call> clusteredCalls = f.computeKMeans(t.getTracePath());
            Boolean sideEffect = resultDistinct.getValue0();

            System.out.println("- Function " + f.getName() + " call(s) : " + f.getCalls().size());

            if (sideEffect) {
                System.out.println("-- Side effect detected for this function");
            }

            System.out.println("-- Distinct call(s) : " + distinctCalls.size());
            System.out.println("-- Detected clusters: " + clusteredCalls.size());
        }
        System.out.println("***************************************************");
        System.out.println();
    }
}
