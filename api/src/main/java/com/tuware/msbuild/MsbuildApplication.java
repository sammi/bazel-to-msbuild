package com.tuware.msbuild;

import com.tuware.msbuild.api.GreeterApi;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@Slf4j
public class MsbuildApplication implements CommandLineRunner{

    private Server server;

    public MsbuildApplication(@Value("${port:50051}") Integer port, GreeterApi greeterApi) {
        server = ServerBuilder.forPort(port)
                .addService(greeterApi)
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(MsbuildApplication.class, args);
    }

    @Override
    public void run(String... args) throws IOException, InterruptedException {

        server.start();

        log.info("Server started, listening on {}", server.getPort());

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.error("*** shutting down gRPC server since JVM is shutting down");
            try {
                if (server != null) {
                    server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
                }
            } catch (InterruptedException e) {
                log.error("server did not shut down gracefully",e);
            }
            log.error("*** server shut down");
        }));

        if (server != null) {
            server.awaitTermination();
        }
    }
}

