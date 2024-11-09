package org.cicdcli.logger

import org.cicdcli._helpers.Envs


class LoggerConf {

    static Integer logLevel() {
        return Envs.getEnv('log.level', false, Integer).value
    }
}
