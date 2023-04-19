package org.blindtester.generator;

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
        for (Object input : getInputs()) {
            hash *= 31*input.hashCode();
        }

        hash *= 31*getOutput().hashCode();

        return hash;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;

        if (other == null)
            return false;

        if (getClass() != other.getClass())
            return false;

        // check inputs
        List<Object> inputs = getInputs();
        int inputsSize = inputs != null ? inputs.size() : 0;

        Call otherCall = (Call)other;
        List<Object> otherInputs= otherCall.getInputs();
        int otherInputsSize = otherInputs != null ? otherInputs.size() : 0;

        // different size of input => Different objects
        if(inputsSize != otherInputsSize){
            return false;
        }

        // compare all inputs
        for (int i=0;i < inputsSize;i++){
            if (!inputs.get(i).equals(otherInputs.get(i))){
                return false;
            }
        }

        // check output
        Object output = getOutput();
        if (!output.equals(otherCall.getOutput())) {
            return false;
        }

        return true;
    }
}
