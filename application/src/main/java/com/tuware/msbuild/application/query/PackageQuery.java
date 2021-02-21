package com.tuware.msbuild.application.query;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
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
public class PackageQuery implements BazelQuery<Build.QueryResult> {

    private ApplicationUtils applicationUtils;

    public PackageQuery(ApplicationUtils applicationUtils) {
        this.applicationUtils = applicationUtils;
    }

    @Override
    public Build.QueryResult query(String bazelProjectRootPath, String query) throws InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            Process process = applicationUtils.startBazelQueryProcess(bazelProjectRootPath, "query", query);
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
