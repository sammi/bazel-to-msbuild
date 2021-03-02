package com.tuware.msbuild.cli;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "com.tuware.msbuild.contract",
        "com.tuware.msbuild.feature",
        "com.tuware.msbuild.adapter",
        "com.tuware.msbuild.cli.command",
})
public class BazelToMsbuildApplicationConfiguration {

}
