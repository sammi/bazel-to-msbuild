package com.tuware.msbuild;

import com.tuware.msbuild.api.GreeterApi;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@SpringBootApplication
public class MsbuildApplication implements CommandLineRunner{
    private static final Logger logger = Logger.getLogger(MsbuildApplication.class.getName());
    private io.grpc.Server server;
    private int port = 50051;

    private GreeterApi greeterApi;

    public MsbuildApplication(GreeterApi greeterApi) {
        this.greeterApi = greeterApi;
    }

    public static void main(String[] args) {
        SpringApplication.run(MsbuildApplication.class, args);
    }

    @Override
    public void run(String... args) throws IOException, InterruptedException {

        server = ServerBuilder.forPort(port)
                .addService(greeterApi)
                .build()
                .start();

        logger.info("Server started, listening on " + port);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            try {
                if (server != null) {
                    server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
                }
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
            System.err.println("*** server shut down");
        }));

        if (server != null) {
            server.awaitTermination();
        }
    }
}

