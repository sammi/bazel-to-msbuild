package com.tuware.msbuild.adapter.query;

import com.tuware.msbuild.contract.adapter.AdapterException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class BazelWindowsProcessBuilder {

    public Process startBazelQueryProcess(String bazelProjectRootPath, String command, String query) throws AdapterException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        //start bazel by cmd /c, so no need find absolute file path for bazel command, the windows will look up by PATH
        //--batch is required, otherwise the target/test-classes/stage1 and target/test-classes/stage3
        //will be holding after tests are done
        //When run mvn clean  again, it will be broken because the target folder cannot be deleted.
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
        try {
            return processBuilder.start();
        } catch (IOException e) {
            throw new AdapterException(String.format("Failed to start bazel query command: %s", processBuilder.command()), e);
        }
    }
}
