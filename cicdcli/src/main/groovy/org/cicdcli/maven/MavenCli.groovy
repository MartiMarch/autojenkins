package org.cicdcli.maven

import org.cicdcli.logger.Logger
import picocli.CommandLine.Parameters
import picocli.CommandLine.Option
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
        Publish,
        Name,
        Version,
        UpdateVersion
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

        @Parameters(
            index = "0",
            description = "pom.xml path"
        )
        String pomPath

        @Option(
            names = ["--isHttps"],
            required = false,
            description = "Path where repository will be cloned"
        )
        boolean isHttps = false

        @Override
        void run() {
            Maven.publish(pomPath, isHttps)
        }
    }

    @CommandLine.Command(
        name = "name",
        mixinStandardHelpOptions = true,
        description = "Build the project and creates JAR file"
    )
    static class Name implements Runnable {

        @Parameters(
            index = "0",
            description = "pom.xml path"
        )
        String pomPath

        @Override
        void run() {
            String name = Maven.name(pomPath)

            if(name == null){
                Logger.error("Can't found project name within pom.xml")
            } else {
                print(name)
            }
        }
    }

    @CommandLine.Command(
        name = "version",
        mixinStandardHelpOptions = true,
        description = "Build the project and creates JAR file"
    )
    static class Version implements Runnable {

        @Parameters(
            index = "0",
            description = "pom.xml path"
        )
        String pomPath

        @Override
        void run() {
            String version = Maven.version(pomPath)

            if(version == null){
                Logger.error("Can't found project version within pom.xml")
            } else {
                print(version)
            }
        }
    }

    @CommandLine.Command(
        name = "updateVersion",
        mixinStandardHelpOptions = true,
        description = "Update pom.xml version"
    )
    static class UpdateVersion implements Runnable {

        @Parameters(
            index = "0",
            description = "Git repository path"
        )
        String repositoryPath

        @Parameters(
            index = "1",
            description = "pom.xml path"
        )
        String pomPath

        @Override
        void run() {
            Maven.updateVersion(repositoryPath, pomPath)
        }
    }

    @Override
    void run() {}
}
