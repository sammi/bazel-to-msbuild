package com.tuware.msbuild.feature;

import java.nio.file.Path;

public interface Feature {

    void buildMsbuildSolutionFromBazelWorkspace(Path bazelWorkspaceFolder, Path msbuildProjectFile, String projectName) throws FeatureException;

}
