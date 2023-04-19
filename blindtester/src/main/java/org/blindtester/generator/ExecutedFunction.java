package org.blindtester.generator;

import com.google.gson.annotations.SerializedName;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExecutedFunction {
    @SerializedName("name")
    private String Name;

    @SerializedName("calls")
    private List<Call> Calls;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<Call> getCalls() {
        return Calls;
    }

    public void setCalls(List<Call> calls) {
        Calls = calls;
    }

    public Pair<Boolean, List<Call>> getDistinctCalls() {
        List<Call> distinctCalls = new ArrayList<>();
        boolean sideEffect = false;

        for (Call c1 : getCalls()) {
            boolean found = false;
            for (Call c2 : distinctCalls) {
                if (c1.equals(c2)) {
                    found = true;
                    break;
                } else {
                    // check if inputs are equals but not the output to detect side effects
                    if (c1.getInputs().equals(c2.getInputs()) && !c1.getOutput().equals(c2.getOutput())) {
                        sideEffect = true;
                    }
                }
            }
            if (!found) {
                distinctCalls.add(c1);
            }
        }

        return new Pair(sideEffect, distinctCalls);
    }

    public List<Call> computeMinimumSetOfCalls() throws Exception {
        // get all distinct calls
        Pair<Boolean, List<Call>> resultDistinct = getDistinctCalls();
        List<Call> distinctCalls = resultDistinct.getValue1();
        Boolean sideEffect = resultDistinct.getValue0();

        // new list that will contain the minimum set of calls
        List<Call> minimumSetCalls = new ArrayList<>();

        // cannot compute when we detected side effects
        if (sideEffect) {
            throw new Exception("Side effect detected => Cannot compute minimum set of calls");
        }

        // Compute and store the number of arguments for each call
        Map<Integer, List<Call>> mapFuncArgs = new HashMap<>();

        for (Call c1 : distinctCalls) {
            int countArgs = c1.getInputs().size();

            if (!mapFuncArgs.containsKey(countArgs)) {
                mapFuncArgs.put(countArgs, new ArrayList<Call>());
            }

            mapFuncArgs.get(countArgs).add(c1); // put last call for now
        }

        // type of inputs and output
        // for each number of args we check that if they have the same type
        // keep cleaned list
        for (var entry : mapFuncArgs.entrySet()) {
            List<Call> argsCalls = entry.getValue();
            keepDifferentInputsOutput(argsCalls);
        }

        // keep cleaned list
        for (var entry : mapFuncArgs.entrySet()) {
            List<Call> calls = entry.getValue();
            for (Call c : calls) {
                minimumSetCalls.add(c);
            }
        }

        return minimumSetCalls;
    }

    public void keepDifferentInputsOutput(List<Call> calls) {

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String sep = System.lineSeparator();
        sb.append(getName() + sep);

        for (Call c : getCalls()) {
            sb.append(String.format("  %s%s", c.toString(), sep));
        }

        return sb.toString();
    }
}
