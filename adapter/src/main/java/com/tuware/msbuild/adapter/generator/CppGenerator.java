package com.tuware.msbuild.adapter.generator;

import com.tuware.msbuild.contract.adapter.AdapterException;
import com.tuware.msbuild.contract.adapter.Generator;
import com.tuware.msbuild.contract.template.CppProjectTemplateData;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class CppGenerator implements Generator<CppProjectTemplateData> {

    private TemplateBuilder templateBuilder;

    public CppGenerator(TemplateBuilder templateBuilder) {
        this.templateBuilder = templateBuilder;
    }

    @Override
    public void generate(CppProjectTemplateData projectTemplateData, String generateProjectFileAbsolutePath) throws AdapterException {
        try {
            String xml = templateBuilder.compileFromTemplateFile("/templates/vcxproj.hbs", projectTemplateData);
            Files.write(Paths.get(generateProjectFileAbsolutePath), xml.getBytes());
        } catch (IOException | URISyntaxException e) {
            throw new AdapterException("Failed to write context to file system.", e);
        }
    }

}
