package com.tuware.msbuild.application.bazel;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PackageQuery implements BazelQuery<Build.QueryResult> {

    @Override
    public Build.QueryResult query(String bazelProjectRootPath, String query) throws InterruptedException {
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
}
