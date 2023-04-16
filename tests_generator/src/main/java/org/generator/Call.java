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

    @Override
    public int hashCode() {
        int hash = 7;
        for (Object input : getInputs()
             ) {
            hash *= 31*input.hashCode();
        }

        hash *= 31*getOutput().hashCode();

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;

        // check inputs
        List<Object> inputs = getInputs();
        int inputsSize = inputs != null ? inputs.size() : 0;

        Call other = (Call)obj;
        List<Object> otherList = other.getInputs();

        for (int i=0;i < inputsSize;i++){
            if (!inputs.get(i).equals(otherList.get(i))){
                return false;
            }
        }

        // check output
        Object output = getOutput();
        if (!output.equals(other.getOutput())) {
            return false;
        }

        return true;
    }
}
