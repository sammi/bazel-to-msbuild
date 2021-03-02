package com.tuware.msbuild.cli;

import com.tuware.msbuild.cli.command.BazelToMsbuildCommand;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import picocli.CommandLine;

@SpringBootApplication
@Import(BazelToMsbuildApplicationConfiguration.class)
public class BazelToMsbuildApplication implements CommandLineRunner {

    private BazelToMsbuildCommand bazelToMsbuildCommand;

    public BazelToMsbuildApplication(BazelToMsbuildCommand bazelToMsbuildCommand) {
        this.bazelToMsbuildCommand = bazelToMsbuildCommand;
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(BazelToMsbuildApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.setLogStartupInfo(false);
        app.run(args);
    }

    @Override
    public void run(String... args) {
        CommandLine commandLine = new CommandLine(bazelToMsbuildCommand);
        commandLine.execute(args);
    }

}
