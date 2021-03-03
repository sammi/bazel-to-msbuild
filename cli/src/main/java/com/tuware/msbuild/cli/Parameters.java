package com.tuware.msbuild.cli;

public class Parameters {
    private String bazelWorkspaceFolder;
    private String msbuildSolutionFolder;
    private String projectName;

    public Parameters(String bazelWorkspaceFolder, String msbuildSolutionFolder, String projectName) {
        this.bazelWorkspaceFolder = bazelWorkspaceFolder;
        this.msbuildSolutionFolder = msbuildSolutionFolder;
        this.projectName = projectName;
    }

    public String getBazelWorkspaceFolder() {
        return bazelWorkspaceFolder;
    }

    public void setBazelWorkspaceFolder(String bazelWorkspaceFolder) {
        this.bazelWorkspaceFolder = bazelWorkspaceFolder;
    }

    public String getMsbuildSolutionFolder() {
        return msbuildSolutionFolder;
    }

    public void setMsbuildSolutionFolder(String msbuildSolutionFolder) {
        this.msbuildSolutionFolder = msbuildSolutionFolder;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
