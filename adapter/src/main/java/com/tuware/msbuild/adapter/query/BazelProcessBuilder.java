package com.tuware.msbuild.adapter.query;

import com.tuware.msbuild.contract.adapter.AdapterException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class BazelProcessBuilder {

    public Process startBazelQueryProcess(File commandWorkingDirectory, List<String> commands) throws AdapterException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.directory(commandWorkingDirectory);
        processBuilder.command(commands);
        try {
            return processBuilder.start();
        } catch (IOException e) {
            throw new AdapterException(String.format("Failed to start bazel query command: %s", processBuilder.command()), e);
        }
    }
}
