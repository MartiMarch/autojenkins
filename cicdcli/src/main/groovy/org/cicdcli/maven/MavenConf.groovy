package org.cicdcli.maven

import org.cicdcli._helpers.Envs


class MavenConf {

    static String getSettingsPath() {
        Envs.getEnv('maven.settings.path', false, String)
    }
}
