package com.tuware.msbuild.adapter.template;

import com.tuware.msbuild.contract.adapter.AdapterException;
import com.tuware.msbuild.contract.adapter.ProjectGeneratorAdapter;
import com.tuware.msbuild.contract.template.CppProjectTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class CppProjectGenerator implements ProjectGeneratorAdapter {

    private TemplateBuilder templateBuilder;

    public CppProjectGenerator(TemplateBuilder templateBuilder) {
        this.templateBuilder = templateBuilder;
    }

    @Override
    public void generateProject(CppProjectTemplate cppProjectTemplate, String projectName, String outputFolder) throws AdapterException {
        try {
            String xml = templateBuilder.compileFromTemplateFile("/templates/vcxproj.hbs", cppProjectTemplate);
            Files.write(Paths.get(String.format("%s/%s.vcxproj", outputFolder, projectName)), xml.getBytes());
        } catch (IOException | URISyntaxException e) {
            throw new AdapterException("Failed to write context to file system.", e);
        }
    }

}
