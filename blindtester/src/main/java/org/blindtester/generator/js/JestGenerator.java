package org.blindtester.generator.js;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import org.apache.commons.lang3.ClassUtils;
import org.blindtester.generator.*;
import org.javatuples.Pair;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class JestGenerator extends Generator {
    private Gson gson;

    private char charIndent = ' ';
    private int sizeIndent = 4;

    public JestGenerator(Trace trace, String lineSeparator) {
        this(trace, lineSeparator, ' ', 4);
    }

    public JestGenerator(Trace trace, String lineSeparator, char charIndent, int sizeIndent) {
        super(trace, lineSeparator);
        gson = new GsonBuilder().disableHtmlEscaping().create();
    }

    private String createTestDescription(String functionName, Call c) {
        if (c.getOutput() != null) {
            return writeFunctionCall(functionName, c) + " should returns " + this.gson.toJson(c.getOutput());
        }

        return functionName + " " + c.getInputs() + " (void) should not cause exception";
    }

    private String Indent() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sizeIndent; i++) {
            sb.append(charIndent);
        }

        return sb.toString();
    }

    private String writeInput(Object input) {
        if (ClassUtils.isPrimitiveOrWrapper(input.getClass())) {
            return input.toString();
        }

        return this.gson.toJson(input);
    }

    private String writeFunctionCall(String functionName, Call c) {
        StringBuilder sb = new StringBuilder();
        sb.append(functionName + "(");
        String input;

        List<Object> inputs = c.getInputs();

        for (int i = 0; i < inputs.size(); i++) {
            input = writeInput(inputs.get(i));
            sb.append(input);
            if (i < inputs.size() - 1) {
                sb.append(",");
            }
        }

        sb.append(")");

        return sb.toString();
    }

    private String writeExpectTest(String functionName, Call c) {
        StringBuilder sb = new StringBuilder();
        sb.append(Indent() + "test('");
        sb.append(createTestDescription(functionName, c));
        sb.append("', () => {");
        sb.append(getLineSeparator());
        sb.append(Indent() + Indent() + "expect(");

        Object output = c.getOutput();

        //primitive type
        if (ClassUtils.isPrimitiveOrWrapper(output.getClass())) {
            sb.append(writeFunctionCall(functionName, c));
            sb.append(").toBe(");
            sb.append(output);
        } else { // object
            // if the calling function returns
            // a buffer we need to convert it to json in the test
            LinkedTreeMap mapOutput = (LinkedTreeMap) output;
            if (mapOutput.containsKey("type") && mapOutput.get("type").equals("Buffer")) {
                sb.append(writeFunctionCall(functionName, c) + ".toJSON()");
            } else {
                sb.append(writeFunctionCall(functionName, c));
            }

            sb.append(").toMatchObject(");

            // convert
            sb.append(this.gson.toJson(output));
        }

        sb.append(");");
        sb.append(getLineSeparator());
        sb.append(Indent() + "});");

        return sb.toString();
    }

    private String writeNoErrorTest(String functionName, Call c) {
        String sb = "test('" + createTestDescription(functionName, c)
                + "', () => {" + getLineSeparator()
                + "expect("
                + writeFunctionCall(functionName, c)
                + ").toBe(undefined);"
                + getLineSeparator() + "});";

        return sb;
    }

    private String transformData(Object o) {
        if (o instanceof String) {
            return "\"" + o + "\"";
        }

        return o.toString();
    }

    public String createTests() throws Exception {
        return createTests(true);
    }

    public String createTests(boolean all) throws Exception {
        StringBuilder sb = new StringBuilder();
        Trace t = getTrace();

        for (Require r : t.getRequires()) {
            sb.append(r.getInclude() + getLineSeparator());
        }

        sb.append(getLineSeparator());
        sb.append("describe('blindtester-" + t.getProjectName() + "', () => {" + getLineSeparator());

        for (ExecutedFunction ef : t.getExecutedFunctions()) {
            // get all distinct calls
            Pair<Boolean, List<Call>> resultDistinct = ef.getDistinctCalls();
            List<Call> distinctCalls = resultDistinct.getValue1();
            Boolean sideEffect = resultDistinct.getValue0();

            // write all tests for all functions in trace
            if (all) {
                for (Call c : distinctCalls) {
                    if (c.getOutput() != null) {
                        sb.append(writeExpectTest(ef.getName(), c) + getLineSeparator());
                    } else {
                        sb.append(writeNoErrorTest(ef.getName(), c) + getLineSeparator());
                    }
                }
            } else { // write only the minimal set
                for (Call c : ef.computeMinimumSetOfCalls()) {
                    if (c.getOutput() != null) {
                        sb.append(writeExpectTest(ef.getName(), c) + getLineSeparator());
                    } else {
                        sb.append(writeNoErrorTest(ef.getName(), c) + getLineSeparator());
                    }
                }
            }
        }

        sb.append("});");
        return sb.toString();
    }

    @Override
    public void writeTests(String path) throws Exception {
        try {
            FileWriter fileWriter = new FileWriter(Paths.get(path, "testblinder-"
                    + getTrace().getProjectName()
                    + ".test.js").toFile());
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write(createTests(false));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new Exception(e);
        }

        System.out.println("Tests generated in : " + path);
    }
}
