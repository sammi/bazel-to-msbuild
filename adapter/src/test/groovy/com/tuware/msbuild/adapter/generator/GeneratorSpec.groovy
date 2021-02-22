package com.tuware.msbuild.adapter.generator

import com.github.jknack.handlebars.Handlebars
import com.tuware.msbuild.contract.template.CppProjectTemplate
import org.springframework.core.io.ClassPathResource
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class GeneratorSpec extends Specification {

    def "Generate default template xml project file when data object is emtpy"(){
        given:
        CppGenerator cppProjectGenerator = new CppGenerator(new TemplateBuilder(new Handlebars()))
        CppProjectTemplate cppProjectTemplate = new CppProjectTemplate()
        String projectName = "test"
        String outputFolder = new ClassPathResource("/").getFile().getAbsoluteFile()
        Path targetFilePath = Paths.get(outputFolder + "/" + projectName + ".vcxproj")

        when:
        cppProjectGenerator.generateProject(cppProjectTemplate, projectName, outputFolder)

        then:
        Files.exists(targetFilePath)
        String xmlHeader = Files.readAllLines(targetFilePath).get(0)
        xmlHeader == '<?xml version="1.0" encoding="utf-8"?>'

        cleanup:
        Files.delete(targetFilePath)
    }

}
