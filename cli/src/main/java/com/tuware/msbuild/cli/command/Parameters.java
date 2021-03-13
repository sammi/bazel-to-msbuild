package com.tuware.msbuild.cli.command;

public class Parameters {
    private String bazelWorkspaceFolder;
    private String projectName;

    public Parameters(String bazelWorkspaceFolder, String projectName) {
        this.bazelWorkspaceFolder = bazelWorkspaceFolder;
        this.projectName = projectName;
    }

    public String getBazelWorkspaceFolder() {
        return bazelWorkspaceFolder;
    }

    public void setBazelWorkspaceFolder(String bazelWorkspaceFolder) {
        this.bazelWorkspaceFolder = bazelWorkspaceFolder;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
