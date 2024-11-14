package org.cicdcli.apk

import org.cicdcli.logger.Logger
import picocli.CommandLine.Parameters
import picocli.CommandLine.Option
import picocli.CommandLine


@CommandLine.Command(
    name = "apk",
    description = "Interact with apk",
    mixinStandardHelpOptions = true,
    subcommands = [List, Add, Version, Delete]
)
class ApkCli implements Runnable{

    @CommandLine.Command(
        name = "list",
        mixinStandardHelpOptions = true,
        description = "Return installed apk packages"
    )
    static class List implements Runnable {

        @Override
        void run() {
            String output = "{\n"
            Apk.listPackages().each { item ->
                output += "  ${item.value.toString()}\n"
            }
            output += "}\n"

            Logger.info(output)
        }
    }

    @CommandLine.Command(
        name = "add",
        mixinStandardHelpOptions = true,
        description = "Add desired package"
    )
    static class Add implements Runnable {

        @Parameters(
            index = "0",
            description = "Package name"
        )
        String packageName

        @Option(
            names = ["-v", "--version"],
            required = false,
            description = "Package version (optional)"
        )
        String packageVersion = ''

        @Override
        void run(){
            Apk.addPackage(packageName, packageVersion)
        }
    }

    @CommandLine.Command(
        name = "version",
        mixinStandardHelpOptions = true,
        description = "Used do known package version"
    )
    static class Version implements Runnable {

        @Parameters(
            index = "0",
            description = "Package name"
        )
        String packageName

        @Override
        void run(){
            String packageVersion = Apk.getPackageVersion(packageName)
            Logger.info(packageVersion)
        }
    }

    @CommandLine.Command(
        name = "delete",
        mixinStandardHelpOptions = true,
        description = "Can delete apk package"
    )
    static class Delete implements Runnable {

        @Parameters(
            index = "0",
            description = "Package name"
        )
        String packageName

        @Override
        void run(){
            Apk.deletePackage(packageName)
        }
    }

    @Override
    void run() {}
}
