package com.tuware.msbuild.adapter.extractor;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.contract.adapter.Extractor;
import com.tuware.msbuild.contract.seed.SolutionSeed;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SolutionExtractor implements Extractor<Build.QueryResult, SolutionSeed> {
    @Override
    public SolutionSeed extract(Build.QueryResult bazelQResult) {
        return SolutionSeed.builder()
                .solutionGuid(UUID.randomUUID())
                .name("solutionName")
                .location("solutionFilePath")
                .projectGuid(UUID.randomUUID())
                .solutionGuid(UUID.randomUUID())
                .build();
    }
}
