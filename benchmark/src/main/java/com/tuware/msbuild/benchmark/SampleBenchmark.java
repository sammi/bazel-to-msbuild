package com.tuware.msbuild.benchmark;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;

@Fork(1)
@Measurement(iterations = 1)
@State(Scope.Benchmark)
@Threads(2)
@BenchmarkMode(Mode.SingleShotTime)
public class SampleBenchmark {

    private static final OkHttpClient client = new OkHttpClient();

    @Param({"http://google.com/search", "http://facebook.com/search"})
    public String url;

    @Param({"benchmark", "c++20"})
    public String keyword;

    @Benchmark
    public String search() throws IOException {
        return fetchContent(url);
    }

    private String fetchContent(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url + "?q=" + keyword)
                .build();
        try (Response response = client.newCall(request).execute()) {
            ResponseBody responseBody = response.body();
            return responseBody == null ? ""  : responseBody.string();
        }
    }


}
