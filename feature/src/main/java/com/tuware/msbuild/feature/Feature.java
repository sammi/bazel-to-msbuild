package com.tuware.msbuild.feature;

import java.nio.file.Path;

public interface Feature {

    void buildSolution(Path bazelWorkspaceFolder, String solutionName) throws FeatureException;

}
