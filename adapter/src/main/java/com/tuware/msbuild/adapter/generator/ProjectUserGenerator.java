package com.tuware.msbuild.adapter.generator;

import com.tuware.msbuild.contract.adapter.AdapterException;
import com.tuware.msbuild.contract.adapter.Generator;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ProjectUserGenerator implements Generator<Object> {

    private final TemplateBuilder templateBuilder;

    public ProjectUserGenerator() {
        this.templateBuilder = new TemplateBuilder();
    }

    @Override
    public String generate(Object templateData) throws AdapterException {
        try {
            return templateBuilder.compileFromTemplateFile(TemplatePaths.projectUserTemplate(), templateData);
        } catch (IOException e) {
            throw new AdapterException("Failed to compile project user xml content.", e);
        }
    }
}
