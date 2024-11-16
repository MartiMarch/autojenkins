package org.cicdcli.git

import picocli.CommandLine.Parameters
import picocli.CommandLine.Option
import picocli.CommandLine


@CommandLine.Command(
    name = "git",
    description = "Interact with git",
    mixinStandardHelpOptions = true,
    subcommands = [
        Version,
        Clone,
        Checkout,
        Reference,
        Push,
        Tag,
        ListTags,
        Commit,
        Configure
    ]
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

    @CommandLine.Command(
        name = "checkout",
        mixinStandardHelpOptions = true,
        description = "Switch to desired repository reference"
    )
    static class Checkout implements Runnable {

        @Parameters(
            index = "0",
            description = "Git repository path"
        )
        String repositoryPath

        @Parameters(
            index = "1",
            description = "Git reference"
        )
        String gitReference

        @Override
        void run() {
           Git.ceckout(repositoryPath, gitReference)
        }
    }

    @CommandLine.Command(
        name = "reference",
        mixinStandardHelpOptions = true,
        description = "Return git reference"
    )
    static class Reference implements Runnable {

        @Parameters(
            index = "0",
            description = "Repository path"
        )
        String repositoryPath

        @Override
        void run() {
            Git.reference(repositoryPath)
        }
    }

    @CommandLine.Command(
        name = "push",
        mixinStandardHelpOptions = true,
        description = "Push reference changes to origin"
    )
    static class Push implements Runnable {

         @Parameters(
             index = "0",
             description = "Git repository path"
         )
         String repositoryPath

        @Parameters(
            index = "1",
            description = "Git reference"
        )
        String reference = null

        @Override
        void run() {
            Git.push(repositoryPath, reference)
        }
    }

    @CommandLine.Command(
        name = "tag",
        mixinStandardHelpOptions = true,
        description = "Used tro create tag"
    )
    static class Tag implements Runnable {

        @Parameters(
            index = "0",
            description = "Git repository path"
        )
        String repositoryPath

        @Parameters(
            index = "1",
            description = "Tag to be created"
        )
        String tag

        @Override
        void run() {
            Git.tag(repositoryPath, tag)
        }
    }

    @CommandLine.Command(
        name = "listTags",
        mixinStandardHelpOptions = true,
        description = "Return all repository tags"
    )
    static class ListTags implements Runnable {

        @Parameters(
            index = "0",
            description = "Git repository path"
        )
        String repositoryPath

        @Override
        void run() {
            Git.listTags(repositoryPath)
        }
    }

    @CommandLine.Command(
        name = "commit",
        mixinStandardHelpOptions = true,
        description = "Creates a commit with reference content"
    )
    static class Commit implements Runnable {

        @Parameters(
            index = "0",
            description = "Git repository path"
        )
        String repositoryPath

        @Parameters(
            index = "1",
            description = "Commit message"
        )
        String commitMessage

        @Override
        void run() {
            Git.commit(repositoryPath, commitMessage)
        }
    }

    @CommandLine.Command(
        name = "configure",
        mixinStandardHelpOptions = true,
        description = "Configure git email and username"
    )
    static class Configure implements Runnable {

        @Override
        void run() {
            Git.configure()
        }
    }

    @Override
    void run() {}
}
