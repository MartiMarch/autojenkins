package org.cicdcli._helpers

import com.fasterxml.jackson.dataformat.xml.XmlMapper


class Xml {

    static Map castXml(String xmlPath) {
        def xmlMapper = new XmlMapper()
        return xmlMapper.readValue(new File(xmlPath), Map.class)
    }

    static void updateXml(String xmlPath, Map<String, Object> map) {
        def xmlMapper = new XmlMapper()
        xmlMapper.writerWithDefaultPrettyPrinter().writeValue(new File(xmlPath), map)
    }
}
