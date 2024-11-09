package org.cicdcli.git

import org.cicdcli.shell.po.ShellOutput
import org.cicdcli.shell.Shell
import org.cicdcli.logger.Logger


class Git {

    static void version(){
        ShellOutput so = Shell.exec("git --version")

        if(so.isError){
            Logger.error(so.error)
            throw new Exception(so.error)
        } else {
            Logger.info(so.output)
        }
    }

}
