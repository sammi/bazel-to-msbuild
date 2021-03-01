package com.tuware.msbuild.adapter.generator;

import com.github.jknack.handlebars.Handlebars;
import com.tuware.msbuild.contract.adapter.AdapterException;
import com.tuware.msbuild.contract.adapter.Generator;
import com.tuware.msbuild.contract.template.CppProjectTemplateData;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;

@Component
public class CppGenerator implements Generator<CppProjectTemplateData> {

    private TemplateBuilder templateBuilder;

    public CppGenerator() {
        this.templateBuilder = new TemplateBuilder(new Handlebars());
    }

    @Override
    public String generate(CppProjectTemplateData templateData) throws AdapterException {
        try {
            return templateBuilder.compileFromTemplateFile("/templates/vcxproj.hbs", templateData);
        } catch (IOException | URISyntaxException e) {
            throw new AdapterException("Failed to compile xml content.", e);
        }
    }

}
