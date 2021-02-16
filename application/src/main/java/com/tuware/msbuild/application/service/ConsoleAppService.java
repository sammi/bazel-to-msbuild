package com.tuware.msbuild.application.service;

import com.tuware.msbuild.application.domain.bazel.Target;
import com.tuware.msbuild.application.domain.msbuild.project.Project;
import com.tuware.msbuild.application.port.ConsoleAppUseCase;

public class ConsoleAppService implements ConsoleAppUseCase {
    @Override
    public Project buildCppConsoleApp(Target target) {
        return null;
    }
}
