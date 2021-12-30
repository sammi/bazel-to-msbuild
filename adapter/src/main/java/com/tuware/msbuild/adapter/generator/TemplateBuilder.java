package com.tuware.msbuild.adapter.generator;

import com.github.jknack.handlebars.Handlebars;
import com.google.common.io.ByteStreams;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
class TemplateBuilder {

    private final Handlebars handlebars;

    public TemplateBuilder() {
        this.handlebars = new Handlebars();
    }

    String compileFromTemplateFile(String xmlTemplateFilePath, Object data) throws IOException {
        InputStream in = getClass().getResourceAsStream(xmlTemplateFilePath);
        byte[] content = in == null ? new byte[]{} : ByteStreams.toByteArray(in);
        return handlebars.prettyPrint(true).compileInline(new String(content)).apply(data);
    }

}
