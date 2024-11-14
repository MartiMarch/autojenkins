package org.cicdcli.git

import picocli.CommandLine.Command


@Command(
    name = "git",
    description = "Interact with git",
    mixinStandardHelpOptions = true,
    subcommands = [Version]
)
class GitCli implements Runnable {

    @Command(
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

    @Override
    void run() {}
}
