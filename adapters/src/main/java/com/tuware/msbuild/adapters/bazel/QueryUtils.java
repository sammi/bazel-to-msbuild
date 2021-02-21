package com.tuware.msbuild.adapters.bazel;

import com.google.devtools.build.lib.analysis.AnalysisProtosV2;
import com.google.devtools.build.lib.query2.proto.proto2api.Build;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class QueryUtils {

    private QueryUtils() {}

    public static Build.QueryResult query(String bazelProjectRootPath, String query) throws InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            Process process = startBazelBuildProcess(bazelProjectRootPath, "query", query);
            Future<Build.QueryResult> future = executor.submit(
                    () -> Build.QueryResult.parseFrom(process.getInputStream())
            );
            return future.get();
        } catch (IOException | ExecutionException e) {
            throw new BazelException(e);
        } finally {
            executor.shutdown();
        }
    }

    public static AnalysisProtosV2.CqueryResult cquery(String bazelProjectRootPath, String query) throws InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            Process process = startBazelBuildProcess(bazelProjectRootPath, "cquery", query);
            Future<AnalysisProtosV2.CqueryResult> future = executor.submit(
                    () -> AnalysisProtosV2.CqueryResult.parseFrom(process.getInputStream())
            );
            return future.get();
        } catch (IOException | ExecutionException e) {
            throw new BazelException(e);
        } finally {
            executor.shutdown();
        }
    }

    private static Process startBazelBuildProcess(String bazelProjectRootPath, String command, String query) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        Process whereProcess = Runtime.getRuntime().exec("where bazel.cmd");
        String bazelAbsolutePath;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(whereProcess.getInputStream()))) {
            bazelAbsolutePath = new File(in.readLine()).getAbsolutePath();
            //--batch is required, otherwise the files will be holding after tests are done, it will break mvn clean later.
            List<String> commands = Arrays.asList(
                    bazelAbsolutePath,
                    "--batch",
                    command,
                    query,
                    "--output=proto");
            processBuilder.directory(new File(bazelProjectRootPath));
            processBuilder.command(commands);
            return processBuilder.start();
        }
    }
}
