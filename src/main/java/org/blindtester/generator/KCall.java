package org.blindtester.generator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class KCall implements Serializable {
    /**
     * List of parameters of a function
     */
    private List<Object> values;

    public KCall() {
    }

    public KCall(Call call) {
        this.values = new ArrayList<Object>();
        for (Object input : call.getInputs()) {
            this.values.add(input.toString());
        }
        // this.values.addAll(call.getInputs());
        // this.values.add(call.getOutput());
    }

    public List<Object> getValues() {
        return values;
    }

    public void setValues(List<Object> values) {
        this.values = values;
    }
}
