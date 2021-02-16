package com.tuware.msbuild.query;

import com.google.devtools.build.lib.analysis.AnalysisProtosV2;
import com.google.devtools.build.lib.query2.proto.proto2api.Build;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class QueryUtils {

    private QueryUtils() {
    }

    public static Build.QueryResult query(String bazelProjectRootPath, String location) throws IOException {
        List<String> commands = Arrays.asList(
                "cmd.exe",
                "/c",
                "bazel",
                "query",
                location,
                "--output=proto");
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.directory(new File(bazelProjectRootPath));
        processBuilder.command(commands);
        Process process = processBuilder.start();
        try(InputStream inputStream = process.getInputStream()) {
            Build.QueryResult queryResult = Build.QueryResult.parseFrom(inputStream);
            process.destroyForcibly();
            return queryResult;
        }
    }

    public static AnalysisProtosV2.CqueryResult cquery(String bazelProjectRootPath, String location) throws IOException {
        List<String> commands = Arrays.asList(
                "cmd.exe",
                "/c",
                "bazel",
                "cquery",
                location,
                "--output=proto");
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.directory(new File(bazelProjectRootPath));
        processBuilder.command(commands);
        Process process = processBuilder.start();
        try(InputStream inputStream = process.getInputStream()) {
            AnalysisProtosV2.CqueryResult cqueryResult = AnalysisProtosV2.CqueryResult.parseFrom(inputStream);
            process.destroyForcibly();
            return cqueryResult;
        }

    }
}
