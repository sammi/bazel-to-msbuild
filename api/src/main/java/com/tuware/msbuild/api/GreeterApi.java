package com.tuware.msbuild.api;

import com.tuware.msbuild.proto.helloworld.GreeterGrpc;
import com.tuware.msbuild.proto.helloworld.HelloReply;
import com.tuware.msbuild.proto.helloworld.HelloRequest;
import com.tuware.msbuild.server.service.ProjectService;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;

@Component
public class GreeterApi extends GreeterGrpc.GreeterImplBase {

    private ProjectService projectService;

    public GreeterApi(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
        try {
            String project = projectService.createProjectXml();
            HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + req.getName() + project).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}
