package org.blindtester.generator;

import com.google.gson.annotations.SerializedName;

/**
 * A requirement to execute the code
 */
public class Require {
    /**
     * The requirement
     */
    @SerializedName("include")
    private String include;

    /**
     * Get the line to include to the code
     *
     * @return the line to include
     */
    public String getInclude() {
        return include;
    }

    /**
     * Override of the toString function ti print a Require
     *
     * @return
     */
    public String toString() {
        return String.format("Include : %s", include);
    }
}
