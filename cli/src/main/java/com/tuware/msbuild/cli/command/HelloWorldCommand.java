package com.tuware.msbuild.cli.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(
        name = "hello",
        description = "Says hello"
)
@Slf4j
public class HelloWorldCommand implements Runnable {

    @Override
    public void run() {
        log.info("Hello World!");
    }
}
