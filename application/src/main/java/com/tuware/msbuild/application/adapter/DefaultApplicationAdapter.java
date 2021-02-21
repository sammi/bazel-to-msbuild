package com.tuware.msbuild.application.adapter;

import com.github.jknack.handlebars.Handlebars;
import com.tuware.msbuild.contract.adapter.AdapterException;
import com.tuware.msbuild.contract.adapter.ApplicationAdapter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Component
public class DefaultApplicationAdapter implements ApplicationAdapter {

    private Handlebars handlebars;
    public DefaultApplicationAdapter(Handlebars handlebars) {
        this.handlebars = handlebars;
    }

    @Override
    public String buildXml(String resourcePath, Object data) throws AdapterException {
        Path path;
        try {
            path = Paths.get(DefaultApplicationAdapter.class.getResource(resourcePath).toURI());
            String template = String.join("\n", Files.readAllLines(path));
            return handlebars.prettyPrint(true).compileInline(template).apply(data);
        } catch (URISyntaxException | IOException e) {
            throw new AdapterException(String.format("Failed to build xml file from template: %s", resourcePath), e);
        }
    }

    @Override
    public Process startBazelQueryProcess(String bazelProjectRootPath, String command, String query) throws AdapterException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        //--batch is required, otherwise the files will be holding after tests are done, it will break mvn clean later.
        List<String> commands = Arrays.asList(
                "cmd",
                "/c",
                "bazel",
                "--batch",
                command,
                query,
                "--output=proto");
        processBuilder.directory(new File(bazelProjectRootPath));
        processBuilder.command(commands);
        try {
            return processBuilder.start();
        } catch (IOException e) {
            throw new AdapterException(e);
        }
    }

}
