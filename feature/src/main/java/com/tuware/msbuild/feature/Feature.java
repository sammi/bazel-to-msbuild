package com.tuware.msbuild.feature;

import java.nio.file.Path;
import java.util.UUID;

public interface Feature {

    void buildSolution(Path bazelWorkspaceFolder, Path msbuildSolutionFolder, String solutionName, UUID solutionUuid) throws FeatureException;

}
