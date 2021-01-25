package com.tuware.msbuild.client;

import com.tuware.msbuild.domain.project.Project;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class BazelProtoQueryClientTest {

    @Test
    void bazel_output_as_proto() throws IOException {

        BazelProtoQueryClient bazelProtoQueryClient = new BazelProtoQueryClient();

        Project project = bazelProtoQueryClient.buildProject(
        "C:\\Users\\songy\\source\\repos\\tuware"
        );

        assertNotNull(project);

        System.out.println(XmlUtils.toXml(project));
    }
}
