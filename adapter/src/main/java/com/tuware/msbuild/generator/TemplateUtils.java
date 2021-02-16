package com.tuware.msbuild.generator;

import com.github.jknack.handlebars.Handlebars;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TemplateUtils {
    private TemplateUtils() {
    }
    public static final Handlebars handlebars = new Handlebars();

    public static String buildXml(String resourcePath, Object data) {
        Path path;
        try {
            path = Paths.get(TemplateUtils.class.getResource(resourcePath).toURI());
            String template = String.join("\n", Files.readAllLines(path));
            return handlebars.prettyPrint(true).compileInline(template).apply(data);
        } catch (URISyntaxException | IOException e) {
            throw new BuildXmlException(String.format("Failed to build xml file from template: %s", resourcePath), e);
        }
    }

}
