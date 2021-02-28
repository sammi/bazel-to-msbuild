package com.tuware.msbuild.adapter.query;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.contract.adapter.AdapterException;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BazelProcessBuilderTest {

    BazelProcessBuilder bazelProcessBuilder = new BazelProcessBuilder();

    @Test
    void startBazelQueryProcess() throws IOException, AdapterException {
        File file = new ClassPathResource("stage3").getFile();
        //start bazel by cmd /c, so no need find absolute file path for bazel command, the windows will look up by PATH
        //--batch is required, otherwise the target/test-classes/stage1 and target/test-classes/stage3
        //will be holding after tests are done
        //When run mvn clean  again, it will be broken because the target folder cannot be deleted.
        List<String> commands = Arrays.asList(
                "cmd",
                "/c",
                "bazel",
                "--batch",
                "query",
                "...",
                "--output=proto");
        Process process = bazelProcessBuilder.startBazelQueryProcess(file.getAbsolutePath(), commands);

        assertTrue(process.isAlive());

        Build.QueryResult queryResult = Build.QueryResult.parseFrom(process.getInputStream());

        assertEquals(3, queryResult.getTargetList().size());

        assertFalse(process.isAlive());
    }
  
}