package com.tuware.msbuild.benchmark;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.File;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SampleBenchmarkTest {

    @Test
    @DisplayName("Run benchmark by using 5 threads")
    public void run() throws RunnerException {
        File file = new File(SampleBenchmark.class.getSimpleName() + ".json");
        Options opt = new OptionsBuilder()
                .include(SampleBenchmark.class.getSimpleName())
                .warmupIterations(1)
                .measurementIterations(1)
                .forks(1)
                .warmupForks(0)
                .threads(50)
                .resultFormat(ResultFormatType.JSON)
                .result(file.getAbsolutePath())
                .build();

        Collection<RunResult> runResultCollection = new Runner(opt).run();

        for(RunResult runResult: runResultCollection) {
            double score = runResult.getPrimaryResult().getScore();
            System.out.println("Average time: " + score + " " + runResult.getPrimaryResult().getScoreUnit());
        }

        assertNotNull(runResultCollection);
    }
}
