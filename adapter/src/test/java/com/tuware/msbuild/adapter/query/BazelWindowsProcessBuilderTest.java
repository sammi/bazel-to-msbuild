package com.tuware.msbuild.adapter.query;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.contract.adapter.AdapterException;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class BazelWindowsProcessBuilderTest {

    BazelWindowsProcessBuilder bazelWindowsProcessBuilder = new BazelWindowsProcessBuilder();

    @Test
    void startBazelQueryProcess() throws IOException, AdapterException {
        File file = new ClassPathResource("stage3").getFile();
        Process process = bazelWindowsProcessBuilder.startBazelQueryProcess(file.getAbsolutePath(), "query", "...");

        assertTrue(process.isAlive());

        Build.QueryResult queryResult = Build.QueryResult.parseFrom(process.getInputStream());

        assertEquals(3, queryResult.getTargetList().size());

        assertFalse(process.isAlive());
    }
  
}