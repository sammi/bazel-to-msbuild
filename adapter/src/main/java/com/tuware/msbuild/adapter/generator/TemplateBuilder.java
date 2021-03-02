package com.tuware.msbuild.adapter.generator;

import com.github.jknack.handlebars.Handlebars;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
class TemplateBuilder {

    private Handlebars handlebars;

    public TemplateBuilder() {
        this.handlebars = new Handlebars();
    }

    String compileFromTemplateFile(String xmlTemplateFilePath, Object data) throws IOException, URISyntaxException {
        Path path = Paths.get(ProjectGenerator.class.getResource(xmlTemplateFilePath).toURI());
        String template = String.join("\n", Files.readAllLines(path));
        return handlebars.prettyPrint(true).compileInline(template).apply(data);
    }

}
