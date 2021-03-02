package com.tuware.msbuild.adapter.generator;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TemplateBuilderTest {

    TemplateBuilder templateBuilder = new TemplateBuilder();

    @Test
    void compileFromTemplateFile() throws IOException, URISyntaxException {
        Map<String, String> data = new HashMap<>();
        data.put("name", "builder");
        String xml = templateBuilder.compileFromTemplateFile("/handlebars/hello.hbs", data);
        assertThat(xml).isEqualTo("Hello builder!");
    }

}
