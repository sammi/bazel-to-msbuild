package com.tuware.msbuild.client;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class BazelProtoQueryClient {

    Build.QueryResult buildQueryResult(String bazelProjectRootPath) throws IOException {
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
}
