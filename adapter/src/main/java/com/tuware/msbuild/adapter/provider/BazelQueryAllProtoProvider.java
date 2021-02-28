package com.tuware.msbuild.adapter.provider;

import com.tuware.msbuild.contract.adapter.Provider;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class BazelQueryAllProtoProvider implements Provider {

    @Override
    public List<String> provide() {
        //Using cmd /c to let windows find bazel from its PATH, otherwise needs provide bazel.cmd absolute path
        //--batch is required, otherwise the bazel process keep live after tests and holds bazel workspace file,
        //and it will block mvn clean command to remove target folder content.
        return Arrays.asList(
                "cmd",
                "/c",
                "bazel",
                "--batch",
                "query",
                "...",
                "--output=proto");
    }
}
