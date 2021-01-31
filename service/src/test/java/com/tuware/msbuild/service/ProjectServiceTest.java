package com.tuware.msbuild.service;

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

class ProjectServiceTest {

    @Test
    void createCppConsoleApplicationProject() throws JAXBException, URISyntaxException, IOException {
        ProjectService projectService = new ProjectService();

        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        UUID id3 = UUID.randomUUID();
        String sourceFilesFilterGuid = String.format("{%s}", id1);
        String headerFilesFilterGuid = String.format("{%s}", id2);
        String resourceFilesFilterGuid = String.format("{%s}", id3);

        String xml = projectService.createCppConsoleApplicationProject(sourceFilesFilterGuid, headerFilesFilterGuid, resourceFilesFilterGuid);

        Path expectFile = Paths.get(getClass().getResource("/App.vcxproj").toURI());

        String expect = String.join("\n", Files.readAllLines(expectFile))
                .replaceAll("4FC737F1-C7A5-4376-A066-2A32D752A2FF", id1.toString())
                .replaceAll("93995380-89BD-4b04-88EB-625FBE52EBFB", id2.toString())
                .replaceAll("67DA6AB6-F800-4c08-8B7A-83BB121AAD01", id3.toString());

        assertThat(xml, equalTo(expect));
    }

}