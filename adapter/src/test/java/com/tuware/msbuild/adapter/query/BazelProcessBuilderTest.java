package com.tuware.msbuild.adapter.query;

import com.tuware.msbuild.contract.adapter.AdapterException;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BazelProcessBuilderTest {

    BazelProcessBuilder bazelProcessBuilder = new BazelProcessBuilder();

    @Test
    void startBazelQueryProcess() throws AdapterException {
        List<String> commands = Arrays.asList("java", "-version");
        Process process = bazelProcessBuilder.startBazelQueryProcess(
            Paths.get(".").toFile(),
            commands
        );
        assertTrue(process.isAlive());
    }
  
}