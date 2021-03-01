package com.tuware.msbuild.adapter.generator;

import com.tuware.msbuild.contract.adapter.AdapterException;
import com.tuware.msbuild.contract.adapter.Generator;
import com.tuware.msbuild.contract.template.CppProjectTemplateData;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;

@Component
public class CppGenerator implements Generator<CppProjectTemplateData> {

    private TemplateBuilder templateBuilder;

    public CppGenerator(TemplateBuilder templateBuilder) {
        this.templateBuilder = templateBuilder;
    }

    @Override
    public String generate(CppProjectTemplateData cppProjectTemplateData) throws AdapterException {
        try {
            return templateBuilder.compileFromTemplateFile("/templates/vcxproj.hbs", cppProjectTemplateData);
        } catch (IOException | URISyntaxException e) {
            throw new AdapterException("Failed to write context to file system.", e);
        }
    }

}
