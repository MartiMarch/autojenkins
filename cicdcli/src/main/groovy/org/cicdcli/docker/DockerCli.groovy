package org.cicdcli.docker

import picocli.CommandLine.Parameters
import picocli.CommandLine


@CommandLine.Command(
    name = "docker",
    description = "Used to manage docker",
    mixinStandardHelpOptions = true,
    subcommands = [
        Initialize
    ]
)
class DockerCli implements Runnable {

    @CommandLine.Command(
        name = "initialize",
        mixinStandardHelpOptions = true,
        description = "Install docker to build containers"
    )
    static class Initialize implements Runnable {

        @Override
        void run() {
            Docker.initialize()
        }
    }

    @Override
    void run(){}
}
