package com.tuware.msbuild.service;

import com.tuware.msbuild.domain.project.Project;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

public class XmlUtils {

    public static String toXml(Project project) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Project.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        StringWriter stringWriter = new StringWriter();
        stringWriter.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        stringWriter.append("\n");
        marshaller.marshal(project, stringWriter);
        return stringWriter.toString();
    }
}
