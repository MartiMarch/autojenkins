package org.cicdcli.apk

import org.cicdcli._helpers.Envs

class ApkConf {

    static String cachePath(){
        return Envs.getEnv('cache.apk', false, String).value
    }

}
