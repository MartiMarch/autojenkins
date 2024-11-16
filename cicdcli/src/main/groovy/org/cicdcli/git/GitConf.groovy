package org.cicdcli.git

import org.cicdcli._helpers.Envs


class GitConf {

    static String gitUsername() {
        return Envs.getEnv('git.username', false, String).value
    }

    static String gitEmail() {
        return Envs.getEnv('git.email', true, String).value
    }

    static String gitToken() {
        return Envs.getEnv('git.token', true, String).value
    }
}
