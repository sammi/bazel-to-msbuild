package com.tuware.msbuild.domain.port;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.domain.msbuild.project.Project;

public interface ConsoleAppUseCase {
    Project consoleApp(Build.QueryResult queryResult);
}
