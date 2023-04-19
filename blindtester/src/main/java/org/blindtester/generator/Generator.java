package org.blindtester.generator;

import org.javatuples.Pair;
import java.util.List;

public abstract class Generator {
    private final Trace trace;
    private final String lineSeparator;

    public Generator(Trace trace, String lineSeparator) {
        this.trace = trace;
        this.lineSeparator = lineSeparator;
    }

    public abstract String createTests() throws Exception;

    public abstract String createTests(boolean all) throws Exception;

    public abstract void writeTests(String path) throws Exception;

    public String getLineSeparator() {
        return lineSeparator;
    }

    public Trace getTrace() {
        return trace;
    }

    public void makeReport() {
        Trace t = getTrace();

        System.out.println("***************************************************");
        System.out.println("Report for project : " + t.getProjectName());
        System.out.println("- " + t.getRequires().size() + " libraries import");

        for (ExecutedFunction f : t.getExecutedFunctions()) {
            // get list of calls for the function
            List<Call> calls = f.getCalls();

            // get distinct calls
            Pair<Boolean, List<Call>> resultDistinct = f.getDistinctCalls();
            List<Call> distinctCalls = resultDistinct.getValue1();
            Boolean sideEffect = resultDistinct.getValue0();

            System.out.println("- Function " + f.getName() + " call(s) : " + f.getCalls().size());

            if (sideEffect) {
                System.out.println("-- Side effect detected for this function");
            }

            System.out.println("-- Distinct call(s) : " + distinctCalls.size());
        }
        System.out.println("***************************************************");
        System.out.println("");
    }
}
