package org.cicdcli.docker

import org.cicdcli._helpers.Envs

class DockerConf {

    static String getDockerHubRepository(){
        return Envs.getEnv('dockerhub.repository', false, String).value
    }

    static String getDockerHubUser() {
        return Envs.getEnv('dockerhub.user', false, String).value
    }

    static String getDockerHubPassword() {
        return Envs.getEnv('dockerhub.password', false, String).value
    }
}
