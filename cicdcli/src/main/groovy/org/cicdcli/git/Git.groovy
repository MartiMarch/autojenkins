package org.cicdcli.git

import org.cicdcli.shell.po.ShellOutput
import org.cicdcli.shell.Shell
import org.cicdcli.logger.Logger


class Git {

    static void version() {
        ShellOutput so = Shell.exec("git --version")
        Shell.checkShellError(so)

        if(so.isError) {
            Logger.error(so.error)
            throw new Exception(so.error)
        } else {
            Logger.info(so.output)
        }
    }

    static void clone(String repository, String repositoryPath = '') {
        if(repositoryPath.isEmpty()) {
            ShellOutput so = Shell.exec("git clone ${repository}")
            Shell.checkShellError(so)
            Logger.info("Repository ${repositoryPath} cloned")
        } else {
            ShellOutput so = Shell.exec("git clone ${repository} ${repositoryPath}")
            Shell.checkShellError(so)
            Logger.info("Repository ${repositoryPath} cloned in ${repositoryPath} path")
        }
    }

    static String reference(String repositoryPath) {
        ShellOutput so = Shell.exec("git -C ${repositoryPath} rev-parse --abbrev-ref HEAD")
        Shell.checkShellError(so)

        return so.output.replaceAll('\n', '')
    }

    static String ceckout(String repositoryPath, String gitRef) {
        ShellOutput so = Shell.exec("git -C ${repositoryPath} checkout ${gitRef}")
        Shell.checkShellError(so)
        Logger.info("Switched to ${gitRef} reference on ${repositoryPath} reporitory path")

        return so.output
    }

    static void push(String repositoryPath, String reference) {
        String httpRepository = getRepoWithToken(repositoryPath)
        ShellOutput so = Shell.exec("git -C ${repositoryPath} push ${httpRepository} ${reference}")
        Shell.checkShellError(so)

        Logger.info("Git reference ${reference} of repository ${reference} pushed to remote")
    }

    static void tag(String repositoryPath, String tag) {
        ShellOutput so = Shell.exec("git -C ${repositoryPath} tag ${tag}")
        Shell.checkShellError(so)

        Logger.info("Tag ${tag} in repository ${repositoryPath} created")
    }

    static List<String> listTags(String repositoryPath) {
        ShellOutput so = Shell.exec("git -C ${repositoryPath} fetch --tags")
        Shell.checkShellError(so)
        so = Shell.exec("git -C ${repositoryPath} tag")

        return so.output.tokenize('\n')
    }

    static void commit(String repositoryPath, String commitMsg) {
        ShellOutput so = Shell.exec("git -C ${repositoryPath} add .")
        Shell.checkShellError(so)
        so = Shell.exec("git -C ${repositoryPath} commit -m '${commitMsg}'")
        Shell.checkShellError(so)
    }

    static void configure() {
        ShellOutput so = Shell.exec("git  config --global user.email '${GitConf.gitUsername()}'")
        Shell.checkShellError(so)
        so = Shell.exec("git config --global user.email '${GitConf.gitEmail()}'")
        Shell.checkShellError(so)
    }

    static List<String> commitsMessages(String repositoryPath) {
        String command = "git -C ${repositoryPath} log --pretty=format:'%s'"
        ShellOutput so = Shell.exec(command)
        Shell.checkShellError(so)

        return so.output.tokenize('\n')
    }

    private static String getRepoWithToken(String repositoryPath) {
        ShellOutput so = Shell.exec("git -C ${repositoryPath} remote get-url origin")
        Shell.checkShellError(so)

        String repository = so.output
        if(repository.startsWith('https://')){
            repository = "https://${GitConf.gitToken()}@${repository.substring('https://'.size(), repository.size())}"
        } else {
            repository = "http://${GitConf.gitToken()}@${repository.substring('http://'.size(), repository.size())}"
        }
        repository = repository.replaceAll("\n", "")

        return repository
    }

}
