package org.cicdcli.maven

import org.cicdcli._helpers.Envs


class MavenConf {

    static String getSettingsPath() {
        Envs.getEnv('maven.settings.path', false, String).value
    }

    static String getNexusDomain() {
        return System.getenv('NEXUS_DOMAIN')
    }

    static String getNexusPort() {
        return System.getenv('NEXUS_PORT')
    }

    static String getRepository() {
        return System.getenv('NEXUS_MVN_REPOSITORY')
    }
}
