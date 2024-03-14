package org.blindtester.generator;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * A call of a function
 */
public class Call {
    /**
     * List of parameters of a function
     */
    @SerializedName("inputs")
    private List<Object> Inputs;

    /**
     * The output of the function
     */
    @SerializedName("output")
    private Object Output;

    /**
     * The output of the function
     */
    @SerializedName("header")
    private String Header;

    public List<String> getHeader() {
        return List.of(Header.split("\n"));
    }

    public void setHeader(String header) {
        Header = header;
    }

    /**
     * Get all inputs for a call
     *
     * @return the list of inputs
     */
    public List<Object> getInputs() {
        return Inputs;
    }
    
    /**
     * Set inputs for a call
     *
     * @param inputs the list of inputs
     */
    public void setInputs(List<Object> inputs) {
        this.Inputs = inputs;
    }

    /**
     * Get the output of the function
     *
     * @return the output
     */
    public Object getOutput() {
        return Output;
    }

    /**
     * Set the output of the function
     *
     * @param output the output
     */
    public void setOutput(Object output) {
        this.Output = output;
    }

    /**
     * Override of the toString function to write a Call
     *
     * @return the string representation of a call
     */
    @Override
    public String toString() {
        return getInputs().toString() + " -> " + getOutput();
    }

    /**
     * Override of the hashCode function to write a specific hashcode with inputs
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        for (Object input : getInputs()) {
            hash *= 31 * input.hashCode();
        }

        hash *= 31 * getOutput().hashCode();

        return hash;
    }

    /**
     * Override of the equals function to compare to Calls
     *
     * @param other the other call to compare
     * @return an indication that if the two objects are equals
     */
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

        Call otherCall = (Call) other;
        List<Object> otherInputs = otherCall.getInputs();
        int otherInputsSize = otherInputs != null ? otherInputs.size() : 0;

        // different size of input => Different objects
        if (inputsSize != otherInputsSize) {
            return false;
        }

        // compare all inputs
        for (int i = 0; i < inputsSize; i++) {
            if (!inputs.get(i).equals(otherInputs.get(i))) {
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
