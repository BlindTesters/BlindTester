package org.generator.generators;

import org.generator.Call;
import org.generator.ExecutedFunction;
import org.generator.Require;
import org.generator.Trace;

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
            if (i instanceof String) {
                input = "'"+i+"'";
            }
            else{
                input = i.toString();
            }

            sb.append(input+",");
        }

        sb.deleteCharAt(sb.length() - 1); //remove last ','
        sb.append(")");

        return sb.toString();
    }

    private String writeExpectTest(String functionName, Call c) {
        StringBuilder sb = new StringBuilder();
        sb.append("test('"+createTestDescription(functionName, c)+"'), () => {" + getLineSeparator());
        sb.append("expect(");
        sb.append(writeFunctionCall(functionName, c)+").toBe(" + c.getOutput() + ");" + getLineSeparator());
        sb.append("});");

        return sb.toString();
    }

    private String writeNoErrorTest(String functionName, Call c) {
        StringBuilder sb = new StringBuilder();
        sb.append("test('"+createTestDescription(functionName, c)+"'), () => {" + getLineSeparator());
        sb.append("expect(");
        sb.append(writeFunctionCall(functionName, c)+").not.toThrow(error)");
        sb.append("});");

        return sb.toString();
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
    public void writeTests (){
        System.out.println(createTests());
    }
}
