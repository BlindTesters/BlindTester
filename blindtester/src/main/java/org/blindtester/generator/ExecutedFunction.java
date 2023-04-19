package org.blindtester.generator;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<Call> getDistinctCalls() {
        // get distinct calls
        List<Call> distinctCalls = this.getCalls().stream().distinct().collect(Collectors.toList());
        return distinctCalls;
    }

    public void setCalls(List<Call> calls) {
        Calls = calls;
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
