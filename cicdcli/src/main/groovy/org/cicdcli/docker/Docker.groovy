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
        ShellOutput so = Shell.exec(
            "sudo -E docker build"
            + " -f ${dockerfilePath}"
            + " -t ${DockerConf.getDockerHubRepository()}/${appName}:${appVersion}"
            + " --build-arg APP_NAME=${appName}"
            + " --build-arg APP_VERSION=${appVersion}"
            + " ."
        )
        Shell.checkShellError(so)
        Logger.info(so.output)
    }
}
