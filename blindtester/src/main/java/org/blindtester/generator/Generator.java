package org.blindtester.generator;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Generator {
    private final Trace trace;
    private final String lineSeparator;

    public Generator(Trace trace, String lineSeparator) {
        this.trace = trace;
        this.lineSeparator = lineSeparator;
    }

    public abstract String createTests();

    public abstract void writeTests(String path) throws IOException;

    public String getLineSeparator() {
        return lineSeparator;
    }

    public Trace getTrace() {
        return trace;
    }

    public void makeReport(){
        Trace t = getTrace();
        System.out.println("Project : " + t.getProjectName());
        System.out.println(t.getRequires().size() + " libraries import");

        for (ExecutedFunction f : t.getExecutedFunctions()) {
            // get list of calls for the function
            List<Call> calls = f.getCalls();

            // get distinct calls
            List<Call> distinctCalls = calls.stream().distinct().collect(Collectors.toList());

            System.out.println("Function " + f.getName() + " called " + f.getCalls().size() + " times");
            System.out.println(" of which " + distinctCalls.size() + " times with different inputs");
        }
    }
}
