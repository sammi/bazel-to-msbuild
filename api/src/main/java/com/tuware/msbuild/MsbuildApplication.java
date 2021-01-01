package com.tuware.msbuild;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.tuware.msbuild.api.GreeterApi;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@Slf4j
public class MsbuildApplication implements CommandLineRunner {

    @Parameter(names = { "--port", "-p" }, description = "server listening port")
    int port = 50051;

    @Parameter(names={"--timeout", "-t"}, description = "max wait seconds for shutting down server")
    int waitShutdownTimeoutSeconds = 30;

    @Autowired
    private GreeterApi greeterApi;

    public static void main(String[] args) {
        SpringApplication.run(MsbuildApplication.class, args);
    }

    @Override
    public void run(String... argv) throws IOException, InterruptedException {

        JCommander.newBuilder()
                .addObject(this)
                .build()
                .parse(argv);

        Server server = ServerBuilder.forPort(port)
                .addService(greeterApi)
                .build();

        server.start();

        log.info("Server started, listening on {}", server.getPort());

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.error("*** shutting down gRPC server on port {} since JVM is shutting down", port);
            try {
                server.shutdown().awaitTermination(waitShutdownTimeoutSeconds, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                log.error("server did not shut down gracefully", e);
            }
            log.error("*** server shut down");
        }));

        server.awaitTermination();
    }
}
