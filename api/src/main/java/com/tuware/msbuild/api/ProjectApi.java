package com.tuware.msbuild.api;

import com.tuware.msbuild.proto.msbuild.ProjectGrpc;
import com.tuware.msbuild.proto.msbuild.HelloReply;
import com.tuware.msbuild.proto.msbuild.HelloRequest;
import com.tuware.msbuild.service.ProjectService;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProjectApi extends ProjectGrpc.ProjectImplBase {

    private ProjectService projectService;

    public ProjectApi(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
        String project = projectService.createProjectXml();
        HelloReply reply = HelloReply.newBuilder().setMessage(project).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

}
