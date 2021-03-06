package com.tuware.msbuild.feature;

import java.nio.file.Path;
import java.util.UUID;

public interface Feature {

    void buildSolution(Path bazelWorkspaceFolder, Path msbuildProjectFile, String projectName, UUID solutionUuid) throws FeatureException;

}
