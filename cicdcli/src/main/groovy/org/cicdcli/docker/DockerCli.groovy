package org.cicdcli.docker

import picocli.CommandLine.Parameters
import picocli.CommandLine.Option
import picocli.CommandLine


@CommandLine.Command(
    name = "docker",
    description = "Used to manage docker",
    mixinStandardHelpOptions = true,
    subcommands = [
        Initialize,
        Build,
        Login,
        Push
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

    @CommandLine.Command(
        name = "build",
        mixinStandardHelpOptions = true,
        description = "Build container using Docker"
    )
    static class Build implements Runnable {

        @Parameters(
            index = "0",
            description = "Application name used to build container reference"
        )
        String appName

        @Parameters(
            index = "1",
            description = "Application version used to build container reference"
        )
        String appVersion

        @Option(
            names = ["-v", "--dockerfilePath"],
            required = false,
            description = "Dockerfile path, 'Dockerfile' by default (optional)"
        )
        String dockerfilePath = 'Dockerfile'

        @Override
        void run() {
            Docker.build(appName, appVersion, dockerfilePath)
        }
    }

    @CommandLine.Command(
        name = "login",
        mixinStandardHelpOptions = true,
        description = "Used to login Docker Hub"
    )
    static class Login implements Runnable {

        @Override
        void run() {
            Docker.login()
        }
    }

    @CommandLine.Command(
        name = "push",
        mixinStandardHelpOptions = true,
        description = "Used to push container"
    )
    static class Push implements Runnable {

        @Parameters(
            index = "0",
            description = "Application name used to build container reference"
        )
        String appName

        @Parameters(
            index = "1",
            description = "Application version used to build container reference"
        )
        String appVersion

        @Override
        void run() {
            Docker.push(appName, appVersion)
        }
    }

    @Override
    void run(){}
}
