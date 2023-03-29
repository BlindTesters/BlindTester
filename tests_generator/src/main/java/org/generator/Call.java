package org.generator;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Call {
    @SerializedName("inputs")
    private List<Object> Inputs;

    @SerializedName("output")
    private Object Output;

    public List<Object> getInputs() {
        return Inputs;
    }

    public void setInputs(List<Object> inputs) {
        Inputs = inputs;
    }

    public Object getOutput() {
        return Output;
    }

    public void setOutput(Object output) {
        Output = output;
    }

    @Override
    public String toString() {
        return getInputs().toString() + " -> " + getOutput();
    }
}
