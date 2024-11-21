package org.cicdcli._helpers

import com.fasterxml.jackson.dataformat.xml.XmlMapper


class Xml {

    static Map castXml(String xmlPath) {
        def xmlMapper = new XmlMapper()
        return xmlMapper.readValue(new File(xmlPath), Map.class)
    }
}
