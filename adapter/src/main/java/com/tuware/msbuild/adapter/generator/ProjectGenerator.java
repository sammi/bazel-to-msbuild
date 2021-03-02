package com.tuware.msbuild.adapter.generator;

import com.tuware.msbuild.contract.adapter.AdapterException;
import com.tuware.msbuild.contract.adapter.Generator;
import com.tuware.msbuild.contract.template.ProjectTemplateData;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;

@Component
public class ProjectGenerator implements Generator<ProjectTemplateData> {

    private TemplateBuilder templateBuilder;

    public ProjectGenerator() {
        this.templateBuilder = new TemplateBuilder();
    }

    @Override
    public String generate(ProjectTemplateData templateData) throws AdapterException {
        try {
            return templateBuilder.compileFromTemplateFile("/templates/vcxproj.hbs", templateData);
        } catch (IOException | URISyntaxException e) {
            throw new AdapterException("Failed to compile project xml content.", e);
        }
    }

}
