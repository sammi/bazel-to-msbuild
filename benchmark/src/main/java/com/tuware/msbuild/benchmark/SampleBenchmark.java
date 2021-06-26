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
public class SampleBenchmark {
    @State(Scope.Benchmark)
    public static class StateObj {
        private String google;
        private String facebook;

        @Setup
        public void setup() {
            google = "http://google.com";
            facebook = "http://facebook.com";
        }

        @TearDown
        public void tearDown() {
            google = null;
            facebook = null;
        }

        public String getGoogle() {
            return google;
        }

        public String getFacebook() {
            return facebook;
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public String google(StateObj stateObj) throws IOException {
        return fetchContent(stateObj.getGoogle());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public String facebook(StateObj stateObj) throws IOException {
        return fetchContent(stateObj.getFacebook());
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
