package com.tuware.msbuild.server.service;

import com.tuware.msbuild.domain.midl.Midl;
import com.tuware.msbuild.domain.project.*;
import com.tuware.msbuild.domain.property.PlatformToolset;
import com.tuware.msbuild.grpc.helloworld.GreeterGrpc;
import com.tuware.msbuild.grpc.helloworld.HelloReply;
import com.tuware.msbuild.grpc.helloworld.HelloRequest;
import io.grpc.stub.StreamObserver;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collections;

public class GreeterService extends GreeterGrpc.GreeterImplBase {

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
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(Project.class);
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
