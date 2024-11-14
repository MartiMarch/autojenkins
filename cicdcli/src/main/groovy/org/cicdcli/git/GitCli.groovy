package org.cicdcli.git

import picocli.CommandLine.Parameters
import picocli.CommandLine.Option
import picocli.CommandLine


@CommandLine.Command(
    name = "git",
    description = "Interact with git",
    mixinStandardHelpOptions = true,
    subcommands = [Version]
)
class GitCli implements Runnable {

    @CommandLine.Command(
        name = "version",
        mixinStandardHelpOptions = true,
        description = "Return git version"
    )
    static class Version implements Runnable {

        @Override
        void run() {
            Git.version()
        }
    }

    @CommandLine.Command(
        name = "clone",
        mixinStandardHelpOptions = true,
        description = "Clone git repository"
    )
    static class Clone implements Runnable {

        @Parameters(
            index = "0",
            description = "Repository HTTP domain"
        )
        String repository

        @Option(
            names = ["--path"],
            required = false,
            description = "Path where repository will be cloned"
        )
        String repositoryPath = ''

        @Override
        void run() {
            Git.clone(repository, repositoryPath)
        }
    }

    @Override
    void run() {}
}
