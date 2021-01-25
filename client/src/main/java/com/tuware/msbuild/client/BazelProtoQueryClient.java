package com.tuware.msbuild.client;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.domain.project.Project;
import com.tuware.msbuild.service.ProjectService;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BazelProtoQueryClient {

    Project buildProject(String bazelProjectRootPath) throws IOException {
        List<String> commands = Arrays.asList(
                "cmd.exe",
                "/c",
                "bazel",
                "query",
                "...",
                "--output=proto");
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.directory(new File(bazelProjectRootPath));
        processBuilder.command(commands);
        Process process = processBuilder.start();

        ProjectService projectService = new ProjectService();

        Build.QueryResult queryResult = Build.QueryResult.parseFrom(process.getInputStream());

        List<String> items = new ArrayList<>();

        queryResult.getTargetList().stream()
            .forEach(target -> items.addAll(target.getRule().getRuleInputList()));

        items.forEach(item -> System.out.println(item));

        return projectService.createProject(queryResult);
    }
}
