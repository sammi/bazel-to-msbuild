package com.tuware.msbuild.client;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BazelProtoQueryClientTest {

    @Test
    void bazel_output_as_proto() throws IOException {

        BazelProtoQueryClient bazelProtoQueryClient = new BazelProtoQueryClient();

        Build.QueryResult queryResult = bazelProtoQueryClient.buildQueryResult(
        "C:\\Users\\songy\\source\\repos\\tuware"
        );

        assertEquals(38, queryResult.getTargetCount());
    }
}
