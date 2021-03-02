package com.tuware.msbuild.adapter.generator;

import com.tuware.msbuild.contract.adapter.AdapterException;
import com.tuware.msbuild.contract.adapter.Generator;
import com.tuware.msbuild.contract.msbuild.project.Project;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;

@Component
public class ProjectFilterGenerator implements Generator<Project> {

    private TemplateBuilder templateBuilder;

    public ProjectFilterGenerator() {
        this.templateBuilder = new TemplateBuilder();
    }

    @Override
    public String generate(Project templateData) throws AdapterException {
        try {
            return templateBuilder.compileFromTemplateFile(TemplatePathProvider.projectFilterTemplate(), templateData);
        } catch (IOException | URISyntaxException e) {
            throw new AdapterException("Failed to compile project filter xml content.", e);
        }
    }
}
