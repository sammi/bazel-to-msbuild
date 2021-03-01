package com.tuware.msbuild.adapter.generator

import com.github.jknack.handlebars.Handlebars
import com.tuware.msbuild.contract.template.CppProjectTemplateData
import spock.lang.Specification

class CppGeneratorSpec extends Specification {

    def "Generate default template xml project file when data object is emtpy"(){
        given:
        CppGenerator cppProjectGenerator = new CppGenerator(new TemplateBuilder(new Handlebars()))
        CppProjectTemplateData cppProjectTemplateData = new CppProjectTemplateData()

        when:
        String projectXml = cppProjectGenerator.generate(cppProjectTemplateData)

        then:
        String xmlHeader = '<?xml version="1.0" encoding="utf-8"?>'
        projectXml.contains(xmlHeader)
    }

}
