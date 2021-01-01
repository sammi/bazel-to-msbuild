package com.tuware.msbuild.api;

import com.tuware.msbuild.proto.helloworld.GreeterGrpc;
import com.tuware.msbuild.proto.helloworld.HelloReply;
import com.tuware.msbuild.proto.helloworld.HelloRequest;
import com.tuware.msbuild.service.ProjectService;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GreeterApi extends GreeterGrpc.GreeterImplBase {

    private ProjectService projectService;

    public GreeterApi(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
        String project = projectService.createProjectXml();
        HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + req.getName() + project).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

}
