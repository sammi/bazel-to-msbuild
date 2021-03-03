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

    @CommandLine.Parameters(parameterConsumer = BazelMsbuildPairConsumer.class, description = "bazel workspace folder and msbuild solution folder")
    private BazelMsbuildPair bazelMsbuildPair;

    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "show usage info")
    private boolean helpRequested = false;

    private CppProjectFeature cppProjectFeature;

    public BazelToMsbuildCommand(CppProjectFeature cppProjectFeature) {
        this.cppProjectFeature = cppProjectFeature;
    }

    @Override
    public void run() {
        String from = bazelMsbuildPair.getBazelWorkspaceFolder();
        String to = bazelMsbuildPair.getMsbuildSolutionFolder();

        Path bazelPath = Paths.get(from);
        Path msBuildPath = Paths.get(to);

        try {
            cppProjectFeature.buildMsbuildSolutionFromBazelWorkspace(bazelPath, msBuildPath, "App");
        } catch (FeatureException e) {
            log.error("Failed to convert bazel workspace: {} to msbuild solution: {}", from, to, e);
            System.exit(1);
        }

    }
}
