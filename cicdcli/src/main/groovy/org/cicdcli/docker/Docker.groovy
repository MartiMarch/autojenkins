package org.cicdcli.docker

import org.cicdcli.apk.Apk
import org.cicdcli.shell.Shell
import org.cicdcli.logger.Logger
import org.cicdcli.shell.po.ShellOutput


class Docker {

    static void initialize(){
        Apk.addPackage("docker")
        Logger.info("Docker installed!")
    }

    static void build(String appName, String appVersion, dockerfilePath = 'Dockerfile') {
        String repo = DockerConf.getDockerHubRepository()

        ShellOutput so = Shell.exec(
            "docker build"
            + " -f ${dockerfilePath}"
            + " -t ${repo}/${appName}:${appVersion}"
            + " --build-arg APP_NAME=${appName}"
            + " --build-arg APP_VERSION=${appVersion}"
            + " ."
        )
        Shell.checkShellError(so)
        Logger.info("Container ${appName}:${appVersion} buld!")
    }

    static void login() {
        String user = DockerConf.getDockerHubUser()
        String password = DockerConf.getDockerHubPassword()

        ShellOutput so = Shell.exec("docker login -u ${user} -p ${password}")
        Shell.checkShellError(so)
        Logger.info("Logged in to Docker Hub!")
    }

    static void push(String appName, String appVersion) {
        String repo = DockerConf.getDockerHubRepository()

        ShellOutput so = Shell.exec("docker push ${repo}/${appName}:${appVersion}")
        Shell.checkShellError(so)
    }
}
