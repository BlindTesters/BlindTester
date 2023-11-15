package org.blindtester.generator.js;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import org.apache.commons.lang3.ClassUtils;
import org.blindtester.generator.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

/**
 * Generator of tests for Jest
 */
public class JestGenerator extends Generator {
    /**
     * JSON parser from the GSON library, use some specific parameters
     */
    private Gson gson;

    /**
     * Character to indent code
     */
    private char charIndent = ' ';

    /**
     * Number of space indentation
     */
    private int sizeIndent = 4;

    /**
     * Constructor the create a Jest Generator with default parameters
     *
     * @param trace         the trace to analyze
     * @param lineSeparator the line separator to use
     */
    public JestGenerator(Trace trace, String lineSeparator) {
        this(trace, lineSeparator, ' ', 4);
    }

    /**
     * Constructor the create a Jest Generator with special parameters for the indentation
     *
     * @param trace         the trace to analyze
     * @param lineSeparator the line separator to use
     * @param charIndent    the character for indentation
     * @param sizeIndent    the size of indentation
     */
    public JestGenerator(Trace trace, String lineSeparator, char charIndent, int sizeIndent) {
        super(trace, lineSeparator);
        gson = new GsonBuilder().disableHtmlEscaping().create();
    }

    /**
     * Create a description for a test by using a function name and the possible return value
     *
     * @param functionName the name of the function
     * @param call         the call to describe
     * @return
     */
    private String createTestDescription(String functionName, Call call) {
        if (call.getOutput() != null) {
            return writeFunctionCall(functionName, call) + " should returns " + this.gson.toJson(call.getOutput());
        }

        return functionName + " " + call.getInputs() + " (void) should not cause exception";
    }

    /**
     * Indent the code with the character for a number of times defined in constructor
     *
     * @return the line indented
     */
    private String Indent() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sizeIndent; i++) {
            sb.append(charIndent);
        }

        return sb.toString();
    }

    /**
     * Write the input parameter for a function for a test
     *
     * @param input the input of the function
     * @return the input formatted
     */
    private String writeInput(Object input) {
        if (input != null && ClassUtils.isPrimitiveOrWrapper(input.getClass())) {
            return input.toString();
        }

        return this.gson.toJson(input);
    }

    /**
     * Write the function correctly to be called in a test
     *
     * @param functionName the name of the function
     * @param call         the call to write
     * @return the function call for a test
     */
    private String writeFunctionCall(String functionName, Call call) {
        StringBuilder sb = new StringBuilder();
        sb.append(functionName + "(");
        String input;

        List<Object> inputs = call.getInputs();

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

    /**
     * Write the result of a jest test
     *
     * @param functionName the name of the function
     * @param call         the call that we need to except values
     * @return the formatted string of the output
     */
    private String writeExpectTest(String functionName, Call call) {
        StringBuilder sb = new StringBuilder();
        sb.append(Indent() + "test('");
        sb.append(createTestDescription(functionName, call));
        sb.append("', () => {");
        sb.append(getLineSeparator());
        sb.append(Indent() + Indent() + "expect(");

        Object output = call.getOutput();

        //primitive type
        if (ClassUtils.isPrimitiveOrWrapper(output.getClass())) {
            sb.append(writeFunctionCall(functionName, call));
            sb.append(").toBe(");
            sb.append(output);
        } else { // object
            if (output instanceof String) {
                sb.append(writeFunctionCall(functionName, call));
            } else {
                // if the calling function returns
                // a buffer we need to convert it to json in the test
                LinkedTreeMap mapOutput = (LinkedTreeMap) output;
                if (mapOutput.containsKey("type") && mapOutput.get("type").equals("Buffer")) {
                    sb.append(writeFunctionCall(functionName, call) + ".toJSON()");
                } else {
                    sb.append("JSON.parse(");
                    sb.append("JSON.stringify(");
                    sb.append(writeFunctionCall(functionName, call));
                    sb.append(")");
                    sb.append(")");
                }
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

    /**
     * Write a test for void function that contains no output
     *
     * @param functionName the name of the function
     * @param call         the call of the function
     * @return the string to write in the test file
     */
    private String writeNoErrorTest(String functionName, Call call) {
        String sb = "test('" + createTestDescription(functionName, call) + "', () => {" + getLineSeparator() + "expect(" + writeFunctionCall(functionName, call) + ").toBe(undefined);" + getLineSeparator() + "});";

        return sb;
    }

    /**
     * @param functionName
     * @param requires
     * @param calls
     * @return
     */
    public String createTests(String functionName, List<Require> requires, List<Call> calls) {
        StringBuilder sb = new StringBuilder();

        for (Require r : requires) {
            sb.append(r.getInclude() + getLineSeparator());
        }

        sb.append(getLineSeparator());

        sb.append("describe('blindtester-" + functionName + "', () => {" + getLineSeparator());

        for (Call c : calls) {
            if (c.getOutput() != null) {
                sb.append(writeExpectTest(functionName, c) + getLineSeparator());
            } else {
                sb.append(writeNoErrorTest(functionName, c) + getLineSeparator());
            }
        }

        sb.append("});");
        return sb.toString();
    }

    /**
     * Write tests to a file for a type of test
     *
     * @param path     the path to write the file
     * @param testType the type of test
     * @throws Exception in case of problem like an unknown test
     */
    @Override
    public void writeTests(String path, String testType) throws Exception {
        try {
            FileWriter fileWriter = new FileWriter(Paths.get(path, "testblinder-" + getTrace().getProjectName() + ".test.js").toFile());
            BufferedWriter writer = new BufferedWriter(fileWriter);

            Trace t = getTrace();

            for (ExecutedFunction ef : t.getExecutedFunctions()) {
                switch (testType) {
                    case "all":
                        writer.write(createTests(ef.getName(), t.getRequires(), ef.getCalls()));
                        break;
                    case "distinct":
                        writer.write(createTests(ef.getName(), t.getRequires(), ef.getDistinctCalls().getValue1()));
                        break;
                    case "minimal":
                        writer.write(createTests(ef.getName(), t.getRequires(), ef.computeMinimumSetOfCalls()));
                        break;
                    case "kmeans":
                        writer.write(createTests(ef.getName(), t.getRequires(), ef.computeKMeans(t.getTracePath())));
                        break;
                    default:
                        throw new Exception("Test not found");
                }
            }

            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new Exception(e);
        }

        System.out.println("Tests generated in : " + path);
    }
}
