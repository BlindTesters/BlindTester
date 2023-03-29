package org.generator;

import com.google.gson.annotations.SerializedName;

import java.nio.file.Path;
import java.util.List;

public class Trace {
    @SerializedName("project_name")
    private String Name;

    @SerializedName("project_path")
    private String Path;

    @SerializedName("main_file")
    private String MainFile;

    @SerializedName("requires")
    private List<Require> Requires;

    @SerializedName("executed_functions")
    private List<ExecutedFunction> ExecutedFunctions;

    public String getProjectName() {
        return Name;
    }

    public void setName(String projectName) {
        Name = projectName;
    }

    public String getProjectPath() {
        return Path;
    }

    public void setPath(String projectPath) {
        Path = projectPath;
    }

    public String getMainFile() {
        return MainFile;
    }

    public void setMainFile(String mainFile) {
        MainFile = mainFile;
    }

    public List<Require> getRequires() {
        return Requires;
    }

    public void setRequires(List<Require> requires) {
        Requires = requires;
    }

    public List<ExecutedFunction> getExecutedFunctions() {
        return ExecutedFunctions;
    }

    public void setExecutedFunctions(List<ExecutedFunction> executedFunctions) {
        ExecutedFunctions = executedFunctions;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String sep = System.lineSeparator();
        sb.append(String.format("Project    : %s" + sep, getProjectName()));
        sb.append(String.format("Path       : %s" + sep, getProjectPath()));
        sb.append(String.format("Main       : %s" + sep, getMainFile()));
        sb.append("Requires   : ");
        for (Require r: getRequires()) {
            sb.append(String.format("* %s" + sep, r.toString()));
        }

        for (ExecutedFunction ef: getExecutedFunctions()) {
            sb.append(String.format("* %s" + sep, ef.toString()));
        }

        return sb.toString();
    }
}
