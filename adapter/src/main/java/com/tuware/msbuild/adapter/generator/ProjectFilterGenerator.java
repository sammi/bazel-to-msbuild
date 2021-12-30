package com.tuware.msbuild.adapter.generator;

import com.tuware.msbuild.contract.adapter.AdapterException;
import com.tuware.msbuild.contract.adapter.Generator;
import com.tuware.msbuild.contract.msbuild.project.Project;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ProjectFilterGenerator implements Generator<Project> {

    private final TemplateBuilder templateBuilder;

    public ProjectFilterGenerator() {
        this.templateBuilder = new TemplateBuilder();
    }

    @Override
    public String generate(Project templateData) throws AdapterException {
        try {
            return templateBuilder.compileFromTemplateFile(TemplatePaths.projectFilterTemplate(), templateData);
        } catch (IOException e) {
            throw new AdapterException("Failed to compile project filter xml content.", e);
        }
    }
}
