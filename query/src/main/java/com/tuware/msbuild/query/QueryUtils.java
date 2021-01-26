package com.tuware.msbuild.query;

import com.google.devtools.build.lib.analysis.AnalysisProtosV2;
import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.service.ProjectService;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class QueryUtils {

    public static Build.QueryResult query(String bazelProjectRootPath, String location) throws IOException {
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

        return Build.QueryResult.parseFrom(process.getInputStream());
    }

    public static AnalysisProtosV2.CqueryResult cquery(String bazelProjectRootPath, String location) throws IOException {
        List<String> commands = Arrays.asList(
                "cmd.exe",
                "/c",
                "bazel",
                "cquery",
                "...",
                "--output=proto");
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.directory(new File(bazelProjectRootPath));
        processBuilder.command(commands);
        Process process = processBuilder.start();

        return AnalysisProtosV2.CqueryResult.parseFrom(process.getInputStream());
    }
}
