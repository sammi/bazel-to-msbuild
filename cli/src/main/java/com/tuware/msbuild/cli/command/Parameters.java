package com.tuware.msbuild.cli.command;

public class Parameters {
    private String bazelWorkspaceFolder;
    private String solutionName;

    public Parameters(String bazelWorkspaceFolder, String solutionName) {
        this.bazelWorkspaceFolder = bazelWorkspaceFolder;
        this.solutionName = solutionName;
    }

    public String getBazelWorkspaceFolder() {
        return bazelWorkspaceFolder;
    }

    public void setBazelWorkspaceFolder(String bazelWorkspaceFolder) {
        this.bazelWorkspaceFolder = bazelWorkspaceFolder;
    }

    public String getSolutionName() {
        return solutionName;
    }

    public void setSolutionName(String solutionName) {
        this.solutionName = solutionName;
    }
}
