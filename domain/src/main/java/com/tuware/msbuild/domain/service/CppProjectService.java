package com.tuware.msbuild.domain.service;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.domain.msbuild.project.Project;
import com.tuware.msbuild.domain.port.ConsoleAppUseCase;

public class CppProjectService implements ConsoleAppUseCase {
    @Override
    public Project consoleApp(Build.QueryResult queryResult) {
        return new Project();
    }
}
