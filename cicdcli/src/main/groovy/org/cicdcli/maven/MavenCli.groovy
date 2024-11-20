package org.cicdcli.maven

import picocli.CommandLine


@CommandLine.Command(
    name = "maven",
    description = "Used to manage maven",
    mixinStandardHelpOptions = true,
    subcommands = [
        Initialize,
        Lint,
        Test,
        Owasp,
        Build,
        Publish
    ]
)
class MavenCli implements Runnable {

    @CommandLine.Command(
        name = "initialize",
        mixinStandardHelpOptions = true,
        description = "Execute all required steps to interact with maven projects"
    )
    static class Initialize implements Runnable {

        @Override
        void run() {
            Maven.initialize()
        }
    }

    @CommandLine.Command(
        name = "lint",
        mixinStandardHelpOptions = true,
        description = "Check the quality of the Maven project via linter"
    )
    static class Lint implements Runnable {

        @Override
        void run() {
            Maven.lint()
        }
    }

    @CommandLine.Command(
        name = "test",
        mixinStandardHelpOptions = true,
        description = "Launch project tests"
    )
    static class Test implements Runnable {

        @Override
        void run() {
            Maven.test()
        }
    }

    @CommandLine.Command(
        name = "owasp",
        mixinStandardHelpOptions = true,
        description = "Owasp dependencies security scan"
    )
    static class Owasp implements Runnable {

        @Override
        void run() {
            Maven.owasp()
        }
    }

    @CommandLine.Command(
        name = "build",
        mixinStandardHelpOptions = true,
        description = "Build the project and creates JAR file"
    )
    static class Build implements Runnable {

        @Override
        void run() {
            Maven.build()
        }
    }

    @CommandLine.Command(
        name = "publish",
        mixinStandardHelpOptions = true,
        description = "Publish JAR file to Nexus"
    )
    static class Publish implements Runnable {

        @Override
        void run() {
            Maven.publish()
        }
    }

    @Override
    void run() {}
}
