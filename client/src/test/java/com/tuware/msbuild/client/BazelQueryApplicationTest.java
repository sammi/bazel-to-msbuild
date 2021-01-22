package com.tuware.msbuild.client;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BazelQueryApplicationTest {

    private static final boolean IS_WINDOWS = System.getProperty("os.name").toLowerCase().startsWith("windows");
    private static final String homeDirectory = System.getProperty("user.home");
    private Consumer<String> consumer = Assert::notNull;

    private static class StreamGobbler implements Runnable {
        private InputStream inputStream;
        private Consumer<String> consumer;

        public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
            this.inputStream = inputStream;
            this.consumer = consumer;
        }

        @Override
        public void run() {
            new BufferedReader(new InputStreamReader(inputStream)).lines().forEach(consumer);
        }
    }

    @Test
    void givenProcess_whenCreatingViaRuntime_shouldSucceed() throws InterruptedException, IOException {
        Process process;
        if (IS_WINDOWS) {
            process = Runtime.getRuntime().exec(String.format("cmd.exe /c dir %s", homeDirectory));
        } else {
            process = Runtime.getRuntime().exec(String.format("sh -c ls %s", homeDirectory));
        }
        StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), consumer);
        Executors.newSingleThreadExecutor().submit(streamGobbler);
        assertEquals(0, process.waitFor());
    }

}
