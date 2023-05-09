package org.blindtester.generator;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * A trace that contain the execution
 */
public class Trace {
    /**
     * The name of the project
     */
    @SerializedName("project_name")
    private String Name;

    /**
     * The path of the project
     */
    @SerializedName("project_path")
    private String Path;

    /**
     * The main file of the project
     */
    @SerializedName("main_file")
    private String MainFile;

    /**
     * The caller of the function
     */
    @SerializedName("caller")
    private String Caller;

    /**
     * The list of requirements
     */
    @SerializedName("requires")
    private List<Require> Requires;

    /**
     * The list of executed functions in the trace
     */
    @SerializedName("executed_functions")
    private List<ExecutedFunction> ExecutedFunctions;

    private String TracePath;

    /**
     * Get the project name
     *
     * @return the project name
     */
    public String getProjectName() {
        return Name;
    }

    /**
     * Get the project path
     *
     * @return the project path
     */
    public String getProjectPath() {
        return Path;
    }

    /**
     * Get the main file
     *
     * @return the main file
     */
    public String getMainFile() {
        return MainFile;
    }

    /**
     * Get the requirements list
     *
     * @return the requirements list
     */
    public List<Require> getRequires() {
        return Requires;
    }

    /**
     * Get the list of executed functions
     *
     * @return the list of executed functions
     */
    public List<ExecutedFunction> getExecutedFunctions() {
        return ExecutedFunctions;
    }

    /**
     * Override of the toString method to print a Trace
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String sep = System.lineSeparator();
        sb.append(String.format("Project    : %s" + sep, getProjectName()));
        sb.append(String.format("Path       : %s" + sep, getProjectPath()));
        sb.append(String.format("Main       : %s" + sep, getMainFile()));
        sb.append("Requires   : " + sep);

        for (Require r : getRequires()) {
            sb.append(String.format("* %s", r.toString()));
        }

        sb.append(sep + "Executed   : " + sep);
        for (ExecutedFunction ef : getExecutedFunctions()) {
            sb.append(String.format("* %s", ef.toString()));
        }

        return sb.toString();
    }

    public void setTracePath(String tracePath) {
        this.TracePath = tracePath;
    }

    public String getTracePath() {
        return TracePath;
    }
}
