package com.tuware.msbuild.application.bazel;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public interface BazelQuery<T> {

    T query(String bazelProjectRootPath, String query) throws InterruptedException;

    default Process startBazelBuildProcess(String bazelProjectRootPath, String command, String query) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        //--batch is required, otherwise the files will be holding after tests are done, it will break mvn clean later.
        List<String> commands = Arrays.asList(
                "cmd",
                "/c",
                "bazel",
                "--batch",
                command,
                query,
                "--output=proto");
        processBuilder.directory(new File(bazelProjectRootPath));
        processBuilder.command(commands);
        return processBuilder.start();
    }

}
