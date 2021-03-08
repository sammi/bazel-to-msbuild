package com.tuware.msbuild.adapter.generator;

import com.github.jknack.handlebars.Handlebars;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
class TemplateBuilder {

    private Handlebars handlebars;

    public TemplateBuilder() {
        this.handlebars = new Handlebars();
    }

    String compileFromTemplateFile(String xmlTemplateFilePath, Object data) throws IOException {
        InputStream in = getClass().getResourceAsStream(xmlTemplateFilePath);
        byte[] content = IOUtils.toByteArray(in);
        return handlebars.prettyPrint(true).compileInline(new String(content)).apply(data);
    }

}
