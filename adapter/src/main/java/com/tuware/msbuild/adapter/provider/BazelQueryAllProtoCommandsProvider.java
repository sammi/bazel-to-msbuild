package com.tuware.msbuild.adapter.provider;

import com.tuware.msbuild.contract.adapter.Provider;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class BazelQueryAllProtoCommandsProvider implements Provider<List<String>> {

    @Override
    public List<String> provide() {
        //--batch is required, otherwise the bazel process keep live after tests and holds bazel workspace file,
        //and it will block mvn clean command to remove target folder content.
        if(SystemUtils.IS_OS_WINDOWS) {
            //Using cmd /c to let windows find bazel from its PATH, otherwise needs provide bazel.cmd absolute path
            return Arrays.asList(
                    "cmd",
                    "/c",
                    "bazel",
                    "--batch",
                    "query",
                    "...",
                    "--output=proto");
        } else {
            return Arrays.asList(
                    "bazel",
                    "--batch",
                    "query",
                    "...",
                    "--output=proto");
        }
    }
}
