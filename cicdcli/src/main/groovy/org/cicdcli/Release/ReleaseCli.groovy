package org.cicdcli.Release

import org.cicdcli.logger.Logger
import picocli.CommandLine.Parameters
import picocli.CommandLine


@CommandLine.Command(
    name = "release",
    description = "Used to manage release",
    mixinStandardHelpOptions = true,
    subcommands = [
        CurrentVersion,
        NextVersion
    ]
)
class ReleaseCli implements Runnable {

    @CommandLine.Command(
        name = "currentVersion",
        mixinStandardHelpOptions = true,
        description = "Used to obtain current version using tags"
    )
    static class CurrentVersion implements Runnable {

        @Parameters(
            index = "0",
            description = "Git repository path"
        )
        String repositoryPath

        @Override
        void run() {
            Logger.info(
                Release.currentVersion(repositoryPath)
            )
        }
    }

    @CommandLine.Command(
        name = "nextVersion",
        mixinStandardHelpOptions = true,
        description = "Return next version using commit messages"
    )
    static class NextVersion implements Runnable {

        @Parameters(
            index = "0",
            description = "Git repository path"
        )
        String repositoryPath

        @Override
        void run() {
            Logger.info(
                Release.nextVersion(repositoryPath)
            )
        }
    }

    @Override
    void run(){}

}
