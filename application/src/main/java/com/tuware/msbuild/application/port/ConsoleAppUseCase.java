package com.tuware.msbuild.application.port;

import com.tuware.msbuild.application.domain.bazel.Target;
import com.tuware.msbuild.application.domain.msbuild.project.Project;

public interface ConsoleAppUseCase {
    Project buildCppConsoleApp(Target target);
}
