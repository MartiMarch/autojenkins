package org.cicdcli.Release

import org.cicdcli.git.Git
import org.cicdcli.shell.Shell
import org.cicdcli.shell.po.ShellOutput


class Release {

    static String currentVersion(String repositoryPath) {
        return Git.listTags(repositoryPath).find {
            it.matches('\\d+\\.\\d+\\.\\d+')
        }
    }

    static String nextVersion(String repositoryPath) {
        String releaseType = Git.commitsMessages(repositoryPath).findResult { String commitMessage ->
            if (isFeatureCommit(commitMessage))
                return 'feature'
            if(isFixCommit(commitMessage))
                return 'fix'
            if(isBreakCommit(commitMessage))
                return 'break'
        } ?: 'feat'

        String currentVersion = currentVersion(repositoryPath)
        if(currentVersion == null) {
            return '0.0.0'
        } else {
            List<String> nextVersion = currentVersion.tokenize('.')
            if(releaseType == 'feat')
                return "${nextVersion[0]}.${nextVersion[1].toInteger()+1}.${nextVersion[2]}"
            if(releaseType == 'fix')
                return "${nextVersion[0]}.${nextVersion[1].toInteger()}.${nextVersion[2].toInteger()+1}"
            if(releaseType == 'break')
                return "${nextVersion[0].toInteger()+1}.${nextVersion[1].toInteger()}.${nextVersion[2]}"

            return '0.0.0'
        }
    }

    static String getName(String repositoryPath) {
        ShellOutput so = Shell.exec("basename \$(git rev-parse --show-toplevel)")
        Shell.checkShellError(so)

        return so.output
    }

    static String target(String repositoryPath) {
        ShellOutput so = Shell.exec("git -C ${repositoryPath} remote show origin")
        Shell.checkShellError(so)

        def headBranchLine = so.output.readLines().find { it.contains("HEAD branch") }
        if(!headBranchLine) {
            return null
        } else {
            return headBranchLine.split(/\s+/).last()
        }
    }

    static String source(String repositoryPath){
        ShellOutput so = Shell.exec("git -C ${repositoryPath} branch -r --contains HEAD")
        Shell.checkShellError(so)

        if(so.output) {
            List<String> branches = so.output.split("\n")
            return branches[0].replaceAll('origin/', '').trim()
        } else {
            return null
        }
    }

    private static boolean isFeatureCommit(String commitMessage) {
        return commitMessage.startsWith('feat')
    }

    private static boolean isFixCommit(String commitMessage) {
        return commitMessage.startsWith('fix')
    }

    private static boolean isBreakCommit(String commitMessage) {
        return commitMessage.startsWith('break')
    }
}
