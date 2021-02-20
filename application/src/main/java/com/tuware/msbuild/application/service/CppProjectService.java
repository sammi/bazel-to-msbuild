package com.tuware.msbuild.application.service;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.application.domain.msbuild.project.Project;
import com.tuware.msbuild.application.port.ConsoleAppUseCase;

public class CppProjectService implements ConsoleAppUseCase {
    @Override
    public Project consoleApp(Build.QueryResult queryResult) {
        return new Project();
    }
}
