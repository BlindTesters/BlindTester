package org.blindtester.generator;

import com.google.gson.annotations.SerializedName;

public class Require {
    @SerializedName("include")
    private String include;

    public String getInclude() {
        return include;
    }

    public void setInclude(String include) {
        this.include = include;
    }

    public String toString() {
        return String.format("Include : %s", include);
    }
}
