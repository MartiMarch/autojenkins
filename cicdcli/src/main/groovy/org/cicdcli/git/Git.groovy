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

    static void clone(String repository, String repositoryPath = ''){
        ShellOutput so

        if(repositoryPath.isEmpty()){
            so = Shell.exec("git clone ${repository}")
            checkShellError(so)

            Logger.info("Repository ${repositoryPath} cloned")
        } else {
            so = Shell.exec("git clone ${repository} ${repositoryPath}")
            checkShellError(so)

            Logger.info("Repository ${repositoryPath} cloned in ${repositoryPath} path")
        }
    }

    static private void checkShellError(ShellOutput so) {
        if(so.isError) {
            Logger.error(so.error)
            throw new Exception(so.error)
        }
    }
}
