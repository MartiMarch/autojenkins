package org.cicdcli.docker

import org.cicdcli._helpers.Envs

class DockerConf {

    static String getDockerHubRepository(){
        return Envs.getEnv('dockerhub.repository', false, String).value
    }
}
