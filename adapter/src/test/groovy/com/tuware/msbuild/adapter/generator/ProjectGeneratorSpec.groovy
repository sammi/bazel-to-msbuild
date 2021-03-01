package com.tuware.msbuild.adapter.generator


import com.tuware.msbuild.contract.template.CppProjectTemplateData
import spock.lang.Specification

class ProjectGeneratorSpec extends Specification {

    def "Generate default template xml project file when data object is emtpy"() {
        given:
        ProjectGenerator cppProjectGenerator = new ProjectGenerator()
        CppProjectTemplateData cppProjectTemplateData = new CppProjectTemplateData()

        when:
        String projectXml = cppProjectGenerator.generate(cppProjectTemplateData)

        then:
        String xmlHeader = '<?xml version="1.0" encoding="utf-8"?>'
        projectXml.contains(xmlHeader)
    }

}
