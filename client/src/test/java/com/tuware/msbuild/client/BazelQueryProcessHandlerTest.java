package com.tuware.msbuild.client;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BazelQueryProcessHandlerTest {
    @Test
    void bazel_output_as_proto() throws IOException {
        List<String> commands = Arrays.asList(
                "cmd.exe",
                "/c",
                "bazel",
                "query",
                "...",
                "--output=proto");
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.directory(new File("C:\\Users\\songy\\source\\repos\\tuware"));
        processBuilder.command(commands);
        Process process = processBuilder.start();
        Build.QueryResult queryResult = Build.QueryResult.parseFrom(process.getInputStream());
        assertEquals(38, queryResult.getTargetCount());
    }
}
