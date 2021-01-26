package com.tuware.msbuild.query;

import com.tuware.msbuild.domain.project.Project;
import com.tuware.msbuild.service.exception.JAXBRuntimeException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

public class XmlUtils {

    public static String toXml(Project project) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Project.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            StringWriter stringWriter = new StringWriter();
            stringWriter.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            marshaller.marshal(project, stringWriter);
            return stringWriter.toString();
        } catch (JAXBException e) {
            throw new JAXBRuntimeException(e);
        }
    }
}
