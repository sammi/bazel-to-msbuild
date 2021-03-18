package com.tuware.msbuild.cli.command;

import com.tuware.msbuild.feature.CppProjectFeature;
import com.tuware.msbuild.feature.FeatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@CommandLine.Command(
        name = "b2m",
        description = "Generate Visualstudio solution from bazel workspace"
)
@Slf4j
public class BazelToMsbuildCommand implements Runnable {

    private final CppProjectFeature cppProjectFeature;
    @CommandLine.Parameters(parameterConsumer = ParametersConsumer.class, description = "bazelWorkspacePath msbuildSolutionPath SolutionName")
    private Parameters parameters;
    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "show usage info")
    private boolean helpRequested = false;

    public BazelToMsbuildCommand(CppProjectFeature cppProjectFeature) {
        this.cppProjectFeature = cppProjectFeature;
    }

    @Override
    public void run() {
        String bazelWorkspaceFolder = parameters.getBazelWorkspaceFolder();
        Path bazelPath = Paths.get(bazelWorkspaceFolder);

        String solutionName = parameters.getSolutionName();

        try {
            cppProjectFeature.buildSolution(bazelPath, solutionName);
        } catch (FeatureException e) {
            log.error("Failed to convert bazel workspace: {} to msbuild solution", bazelWorkspaceFolder, e);
            System.exit(1);
        }

    }
}
