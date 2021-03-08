package com.tuware.msbuild.adapter.generator;

import com.tuware.msbuild.contract.adapter.AdapterException;
import com.tuware.msbuild.contract.adapter.Generator;
import com.tuware.msbuild.contract.template.ProjectTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ProjectGenerator implements Generator<ProjectTemplate> {

    private TemplateBuilder templateBuilder;

    public ProjectGenerator() {
        this.templateBuilder = new TemplateBuilder();
    }

    @Override
    public String generate(ProjectTemplate templateData) throws AdapterException {
        try {
            return templateBuilder.compileFromTemplateFile(TemplatePaths.projectTemplate(), templateData);
        } catch (IOException e) {
            throw new AdapterException("Failed to compile project xml content.", e);
        }
    }

}
