package org.cicdcli.apk.po


class ApkPackage {
    String name
    String version
    String organization
    List<String> collection

    String toString() {

        return "{"                                    +
            "\"name\": \"${name}\", "                 +
            "\"version\": \"${version}\", "           +
            "\"organization\": \"${organization}\", " +
            "\"collection\": \"${collection}\""       +
        "}"
    }
}
