package com.tuware.msbuild.service;

import com.github.jknack.handlebars.Handlebars;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProjectServiceTest {

    private ProjectService projectService;

    @BeforeEach
    void setup() {
        Handlebars handlebars = new Handlebars();
        projectService = new ProjectService(handlebars);
    }

    @Test
    void createProjectFilters() throws URISyntaxException, IOException {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        UUID id3 = UUID.randomUUID();
        String sourceFilesFilterGuid = String.format("{%s}", id1);
        String headerFilesFilterGuid = String.format("{%s}", id2);
        String resourceFilesFilterGuid = String.format("{%s}", id3);

        String xml = projectService.createProjectFilters(sourceFilesFilterGuid, headerFilesFilterGuid, resourceFilesFilterGuid);

        assertThat(xml, equalTo(
                readTextFromFile("/App.vcxproj.filters")
                .replaceAll("4FC737F1-C7A5-4376-A066-2A32D752A2FF", id1.toString())
                .replaceAll("93995380-89BD-4b04-88EB-625FBE52EBFB", id2.toString())
                .replaceAll("67DA6AB6-F800-4c08-8B7A-83BB121AAD01", id3.toString())
        ));
    }


    @Test
    void createProjectUser() throws URISyntaxException, IOException {
        assertThat(projectService.createProjectUser(), equalTo(readTextFromFile("/App.vcxproj.user")));
    }

    @Test
    void createProject() throws IOException, URISyntaxException, JAXBException {
        String projectGuild = "{7299f8e2-6e4d-486a-927b-862f47b0d2a0}";
        assertEquals(
            readTextFromFile("/App.vcxproj"),
            projectService.createProject("ConsoleApplication1.cpp",projectGuild)
        );
    }

    private String readTextFromFile(String resourceRelativePath) throws URISyntaxException, IOException {
        Path expectFile = Paths.get(getClass().getResource(resourceRelativePath).toURI());
        return String.join("\n", Files.readAllLines(expectFile));
    }

}