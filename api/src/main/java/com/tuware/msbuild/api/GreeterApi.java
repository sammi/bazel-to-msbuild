package com.tuware.msbuild.api;

import com.tuware.msbuild.domain.project.*;
import com.tuware.msbuild.proto.helloworld.GreeterGrpc;
import com.tuware.msbuild.proto.helloworld.HelloReply;
import com.tuware.msbuild.proto.helloworld.HelloRequest;
import com.tuware.msbuild.server.service.ProjectService;
import io.grpc.stub.StreamObserver;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

public class GreeterApi extends GreeterGrpc.GreeterImplBase {

    @Override
    public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {

        ProjectService projectService = new ProjectService();

        Project project = projectService.createProject();

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
