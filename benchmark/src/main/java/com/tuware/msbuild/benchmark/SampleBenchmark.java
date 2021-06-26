package com.tuware.msbuild.benchmark;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class SampleBenchmark {

    @Param({"http://google.com", "http://facebook.com"})
    public String url;

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public String search() throws IOException {
        return fetchContent(url);
    }

    private String fetchContent(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            ResponseBody responseBody = response.body();
            return responseBody == null ? ""  : responseBody.string();
        }
    }


}
