package com.tuware.msbuild.adapter.xml;

import com.github.jknack.handlebars.Handlebars;
import com.tuware.msbuild.contract.adapter.AdapterException;
import com.tuware.msbuild.contract.adapter.XmlFileGeneratorAdapter;
import com.tuware.msbuild.contract.template.CppProjectTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class CppProjectFileGeneratorAdapter implements XmlFileGeneratorAdapter {

    private Handlebars handlebars;

    public CppProjectFileGeneratorAdapter(Handlebars handlebars) {
        this.handlebars = handlebars;
    }

    @Override
    public void generateXmlFiles(CppProjectTemplate cppProjectTemplate, String projectName) throws AdapterException {
        try {
            String xml = buildXml("/templates/vcxproj.hbs", cppProjectTemplate);
            Files.write(Paths.get(String.format("%s.sln", projectName)), xml.getBytes());
        } catch (IOException | URISyntaxException e) {
            throw new AdapterException("Failed to write context to file system.", e);
        }
    }

    private String buildXml(String xmlTemplateFilePath, Object data) throws IOException, URISyntaxException {
        Path path = Paths.get(CppProjectFileGeneratorAdapter.class.getResource(xmlTemplateFilePath).toURI());
        String template = String.join("\n", Files.readAllLines(path));
        return handlebars.prettyPrint(true).compileInline(template).apply(data);
    }

}
