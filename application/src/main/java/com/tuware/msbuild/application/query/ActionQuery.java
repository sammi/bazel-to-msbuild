package com.tuware.msbuild.application.query;

import com.google.devtools.build.lib.analysis.AnalysisProtosV2;
import com.tuware.msbuild.application.exception.BazelException;
import com.tuware.msbuild.application.utils.ApplicationUtils;
import com.tuware.msbuild.io.BazelQuery;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
public class ActionQuery implements BazelQuery<AnalysisProtosV2.CqueryResult> {

    private ApplicationUtils applicationUtils;
    public ActionQuery(ApplicationUtils applicationUtils) {
        this.applicationUtils = applicationUtils;
    }

    @Override
    public AnalysisProtosV2.CqueryResult query(String bazelProjectRootPath, String query) throws InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            Process process = applicationUtils.startBazelQueryProcess(bazelProjectRootPath, "cquery", query);
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
