package com.tuware.msbuild.cli;

import com.tuware.msbuild.cli.command.HelloWorldCommand;
import com.tuware.msbuild.feature.CppProjectFeature;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import picocli.CommandLine;

@SpringBootApplication
@ComponentScan(basePackages = {
    "com.tuware.msbuild.contract",
    "com.tuware.msbuild.feature",
    "com.tuware.msbuild.adapter",
    "com.tuware.msbuild.cli.command",
})
public class BazelToMsbuildApplication implements CommandLineRunner {

    private CppProjectFeature cppProjectFeature;
    private HelloWorldCommand helloWorldCommand;

    public BazelToMsbuildApplication(CppProjectFeature cppProjectFeature, HelloWorldCommand helloWorldCommand) {
        this.cppProjectFeature = cppProjectFeature;
        this.helloWorldCommand = helloWorldCommand;
    }

    public static void main(String[] args) {
        SpringApplication.run(BazelToMsbuildApplication.class, args);
    }

    @Override
    public void run(String... args) {
        assert(cppProjectFeature != null);
        CommandLine commandLine = new CommandLine(helloWorldCommand);
        commandLine.execute(args);
    }

}
