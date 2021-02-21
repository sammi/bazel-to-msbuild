package com.tuware.msbuild.adapter;

import com.tuware.msbuild.contract.adapter.AdapterException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class BazelProcessBuilder {

    public Process startBazelQueryProcess(String bazelProjectRootPath, String command, String query) throws AdapterException {
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
        try {
            return processBuilder.start();
        } catch (IOException e) {
            throw new AdapterException(e);
        }
    }
}
