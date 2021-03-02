package com.tuware.msbuild.adapter.generator;

import com.tuware.msbuild.contract.adapter.Generator;
import com.tuware.msbuild.contract.msbuild.project.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectFilterGenerator implements Generator<Project> {
    @Override
    public String generate(Project templateData) {
        return "";
    }
}
