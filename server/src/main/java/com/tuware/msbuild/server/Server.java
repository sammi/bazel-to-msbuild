package com.tuware.msbuild.server;

import com.tuware.msbuild.domain.midl.Midl;
import com.tuware.msbuild.domain.project.*;
import com.tuware.msbuild.domain.property.PlatformToolset;
import com.tuware.msbuild.grpc.helloworld.GreeterGrpc;
import com.tuware.msbuild.grpc.helloworld.HelloReply;
import com.tuware.msbuild.grpc.helloworld.HelloRequest;
import io.grpc.ServerBuilder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.tuware.msbuild.domain.project.Project;
import io.grpc.stub.StreamObserver;

/**
 * Server that manages startup/shutdown of a {@code Greeter} server.
 */
public class Server {
    private static final Logger logger = Logger.getLogger(Server.class.getName());

    private io.grpc.Server server;

    private void start() throws IOException {
        /* The port on which the server should run */
        int port = 50051;
        server = ServerBuilder.forPort(port)
                .addService(new GreeterImpl())
                .build()
                .start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Use stderr here since the logger may have been reset by its JVM shutdown hook.
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            try {
                Server.this.stop();
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
            System.err.println("*** server shut down");
        }));
    }

    private void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main launches the server from the command line.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final Server server = new Server();
        server.start();
        server.blockUntilShutdown();
    }

    static class GreeterImpl extends GreeterGrpc.GreeterImplBase {

        @Override
        public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {

            Project project = Project.builder()
                    .xmlns("http://schemas.microsoft.com/developer/msbuild/2003")
                    .defaultTargets("Build")
                    .initialTargets("Test")
                    .treatAsLocalProperty("MyProperty")
                    .sdk("DotNet.SDK.4.0")
                    .toolsVersion("16.0.0")
                    .itemGroupList(
                            Arrays.asList(
                                    ItemGroup.builder().midlList(
                                            Collections.singletonList(
                                                    Midl.builder()
                                                            .include("Config.idl")
                                                            .dependentUpon("Config.xml")
                                                            .build()
                                            )
                                    ).build(),
                                    ItemGroup.builder().projectConfigurationList(
                                            Collections.singletonList(
                                                    ProjectConfiguration.builder()
                                                            .include("Debug|x64")
                                                            .configuration("Debug")
                                                            .platform("x64")
                                                            .build()
                                            )
                                    ).build(),
                                    ItemGroup.builder().clIncludeList(
                                            Collections.singletonList(
                                                    ClInclude.builder()
                                                            .include("Cluster.h")
                                                            .dependentUpon("Cluster.idl")
                                                            .build()
                                            )
                                    ).build()
                            )
                    )
                    .propertyGroupList(Collections.singletonList(
                            PropertyGroup.builder().platformToolsetList(
                                    Arrays.asList(
                                            PlatformToolset.builder().condition("'$(VisualStudioVersion)' == '15.0'").value("v141").build(),
                                            PlatformToolset.builder().condition("'$(VisualStudioVersion)' == '16.0'").value("v142").build()
                                    )
                            ).build()
                    ))
                    .build();
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(Project.class);
                Marshaller marshaller = jaxbContext.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                StringWriter stringWriter = new StringWriter();
                stringWriter.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
                marshaller.marshal(project, stringWriter);

                System.out.print(stringWriter.toString());

                HelloReply reply = HelloReply.newBuilder().setMessage(
                        "Hello " + req.getName() + stringWriter.toString()
                ).build();
                responseObserver.onNext(reply);
                responseObserver.onCompleted();

            } catch (JAXBException e) {
                e.printStackTrace();
            }

        }
    }
}
