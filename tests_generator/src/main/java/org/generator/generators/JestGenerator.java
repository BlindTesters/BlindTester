package org.generator.generators;

import org.generator.Call;
import org.generator.ExecutedFunction;
import org.generator.Require;
import org.generator.Trace;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JestGenerator extends Generator {
    public JestGenerator(Trace trace, String lineSeparator){
        super(trace, lineSeparator);
    }

    private String createTestDescription(String functionName, Call c){
        if(c.getOutput() != null){
            return functionName + " " + c.getInputs() + " should returns " + c.getOutput();
        }

        return functionName + " " + c.getInputs() + " (void) should not cause exception";
    }

    private String writeFunctionCall(String functionName, Call c) {
        StringBuilder sb = new StringBuilder();
        sb.append(functionName+"(");
        String input;

        for(Object i : c.getInputs()){
            input = transformData(i);

            sb.append(input+",");
        }

        sb.deleteCharAt(sb.length() - 1); //remove last ','
        sb.append(")");

        return sb.toString();
    }

    private String writeExpectTest(String functionName, Call c) {
        StringBuilder sb = new StringBuilder();
        sb.append("test('"+createTestDescription(functionName, c)+"', () => {" + getLineSeparator());
        sb.append("expect(");
        sb.append(writeFunctionCall(functionName, c)+").toBe(" + transformData(c.getOutput()) + ");");
        sb.append(getLineSeparator() + "});");

        return sb.toString();
    }

    private String writeNoErrorTest(String functionName, Call c) {
        StringBuilder sb = new StringBuilder();
        sb.append("test('"+createTestDescription(functionName, c)+"', () => {" + getLineSeparator());
        sb.append("expect(");
        sb.append(writeFunctionCall(functionName, c)+").toBe(undefined);");
        sb.append(getLineSeparator() + "});");

        return sb.toString();
    }

    private String transformData(Object o){
        if (o instanceof String) {
            return "'"+o+"'";
        }

        return o.toString();
    }

    public String createTests() {
        StringBuilder sb = new StringBuilder();
        Trace t = getTrace();

        for(Require r : t.getRequires()){
            sb.append(r.getInclude() + getLineSeparator());
        }

        sb.append(getLineSeparator());

        sb.append("describe('auto-" + t.getProjectName() + "', () => {" + getLineSeparator());

        for(ExecutedFunction ef : t.getExecutedFunctions()){
            for(Call c : ef.getCalls()){
                if (c.getOutput() != null){
                    sb.append(writeExpectTest(ef.getName(), c) + getLineSeparator());
                }
                else{
                    sb.append(writeNoErrorTest(ef.getName(), c) + getLineSeparator());
                }
            }
        }

        sb.append("});");
        return sb.toString();
    }

    @Override
    public void writeTests (String path) {
        try {
            FileWriter fileWriter = new FileWriter(Paths.get(path, "auto-"+getTrace().getProjectName()+".test.js").toFile());
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write(createTests());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Tests generated in : " + path);
    }
}
