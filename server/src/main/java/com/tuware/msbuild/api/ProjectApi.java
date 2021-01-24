package com.tuware.msbuild.api;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.proto.MsvcProjectReply;
import com.tuware.msbuild.proto.ProjectGrpc;
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
    public void buildMsvcProject(Build.QueryResult req, StreamObserver<MsvcProjectReply> responseObserver) {
        String project = projectService.createProjectXml();
        MsvcProjectReply reply = MsvcProjectReply.newBuilder().setMessage(project).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

}
