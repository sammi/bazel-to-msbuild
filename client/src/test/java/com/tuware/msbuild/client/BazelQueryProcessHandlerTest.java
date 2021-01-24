package com.tuware.msbuild.client;

import com.zaxxer.nuprocess.NuProcess;
import com.zaxxer.nuprocess.NuProcessBuilder;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

class BazelQueryProcessHandlerTest {
    @Test
    void bazel_output_as_xml() throws InterruptedException {
        NuProcessBuilder nuProcessBuilder = new NuProcessBuilder(Arrays.asList(
                "cmd.exe",
                "/c",
                "bazel",
                "query",
                "...",
                "--output=xml"));
        Path bazelProjectRootPath = Paths.get("C:\\Users\\songy\\source\\repos\\tuware");
        nuProcessBuilder.setCwd(bazelProjectRootPath);
        BazelQueryProcessHandler bazelQueryProcessHandler = new BazelQueryProcessHandler();
        nuProcessBuilder.setProcessListener(bazelQueryProcessHandler);
        NuProcess nuProcess = nuProcessBuilder.start();
        nuProcess.wantWrite();
        nuProcess.waitFor(0, TimeUnit.SECONDS);
    }
}
