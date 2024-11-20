package org.cicdcli.docker

import org.cicdcli.apk.Apk
import org.cicdcli.logger.Logger


class Docker {

    static void initialize(){
        Apk.addPackage("docker")
        Logger.info("Docker installed!")
    }
}
