package com.tuware.msbuild.adapter.generator

import com.github.jknack.handlebars.Handlebars
import com.tuware.msbuild.contract.template.CppProjectTemplateData
import org.springframework.core.io.ClassPathResource
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class CppGeneratorSpec extends Specification {

    def "Generate default template xml project file when data object is emtpy"(){
        given:
        CppGenerator cppProjectGenerator = new CppGenerator(new TemplateBuilder(new Handlebars()))
        CppProjectTemplateData cppProjectTemplateData = new CppProjectTemplateData()

        String generateSolutionAbsolutePath = String.format("%s\\test.vcxproj",
                new ClassPathResource("/").getFile().getAbsoluteFile()
        )

        Path targetFilePath = Paths.get(generateSolutionAbsolutePath)

        when:
        cppProjectGenerator.generate(cppProjectTemplateData, generateSolutionAbsolutePath)

        then:
        Files.exists(targetFilePath)
        String xmlHeader = Files.readAllLines(targetFilePath).get(0)
        xmlHeader == '<?xml version="1.0" encoding="utf-8"?>'

        cleanup:
        Files.delete(targetFilePath)
    }

}
