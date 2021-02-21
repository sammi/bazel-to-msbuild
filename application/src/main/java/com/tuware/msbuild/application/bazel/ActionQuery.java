package com.tuware.msbuild.application.bazel;

import com.google.devtools.build.lib.analysis.AnalysisProtosV2;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ActionQuery implements BazelQuery<AnalysisProtosV2.CqueryResult> {

    @Override
    public AnalysisProtosV2.CqueryResult query(String bazelProjectRootPath, String query) throws InterruptedException {
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

}
