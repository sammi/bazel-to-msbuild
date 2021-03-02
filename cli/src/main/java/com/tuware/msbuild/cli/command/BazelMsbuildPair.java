package com.tuware.msbuild.cli.command;

public class BazelMsbuildPair {
    private String bazelWorkspaceFolder;
    private String msbuildSolutionFolder;

    public BazelMsbuildPair(String bazelWorkspaceFolder, String msbuildSolutionFolder) {
        this.bazelWorkspaceFolder = bazelWorkspaceFolder;
        this.msbuildSolutionFolder = msbuildSolutionFolder;
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
}
