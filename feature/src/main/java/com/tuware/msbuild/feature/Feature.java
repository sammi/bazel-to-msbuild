package com.tuware.msbuild.feature;

import java.nio.file.Path;
import java.util.UUID;

public interface Feature {

    void buildSingleProjectSolution(Path bazelWorkspaceFolder, Path msbuildProjectFile, String projectName, UUID solutionUuid, UUID projectUuid) throws FeatureException;

}
