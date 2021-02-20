package com.tuware.msbuild.application.port;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.application.domain.msbuild.project.Project;

public interface ConsoleAppUseCase {
    Project consoleApp(Build.QueryResult queryResult);
}
