package org.blindtester.generator.js;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.ClassUtils;
import org.blindtester.generator.*;

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
        for (int i=0;i<sizeIndent;i++){
            sb.append(charIndent);
        }

        return sb.toString();
    }

    private String writeInput(Object input){
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

        for(int i=0;i<inputs.size();i++) {
            input = writeInput(inputs.get(i));
            sb.append(input);
            if (i < inputs.size()-1){
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
        sb.append(Indent()+Indent()+"expect(");
        sb.append(writeFunctionCall(functionName, c));

        Object output = c.getOutput();

        //primitive type
        if (ClassUtils.isPrimitiveOrWrapper(output.getClass())) {
            sb.append(").toBe(");
            sb.append(output);
        }
        else {
            //object
            sb.append(").toMatchObject(");
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

    public String createTests() {
        StringBuilder sb = new StringBuilder();
        Trace t = getTrace();

        for (Require r : t.getRequires()) {
            sb.append(r.getInclude() + getLineSeparator());
        }

        sb.append(getLineSeparator());
        sb.append("describe('blindtester-" + t.getProjectName() + "', () => {" + getLineSeparator());

        for (ExecutedFunction ef : t.getExecutedFunctions()) {
            for (Call c : ef.getCalls()) {
                if (c.getOutput() != null) {
                    sb.append(writeExpectTest(ef.getName(), c) + getLineSeparator());
                } else {
                    sb.append(writeNoErrorTest(ef.getName(), c) + getLineSeparator());
                }
            }
        }

        sb.append("});");
        return sb.toString();
    }

    @Override
    public void writeTests(String path) {
        try {
            FileWriter fileWriter = new FileWriter(Paths.get(path, "auto-"
                    + getTrace().getProjectName()
                    + ".test.js").toFile());
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write(createTests());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Tests generated in : " + path);
    }
}
