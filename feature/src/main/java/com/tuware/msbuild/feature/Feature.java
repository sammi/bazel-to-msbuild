package com.tuware.msbuild.feature;

public interface Feature {
    void buildMsbuildSolutionFromBazelWorkspace(String bazelWorkspaceAbsolutePath, String msbuildSolutionAbsolutePath) throws FeatureException;
}
