package org.cicdcli

import org.cicdcli.git.GitCli
import org.cicdcli.apk.ApkCli
import org.cicdcli.asdf.AsdfCli
import org.cicdcli.shell.ShellCli
import org.cicdcli.logger.LoggerCli
import picocli.CommandLine
import picocli.CommandLine.Command


@Command(
    name = "cicdcli",
    mixinStandardHelpOptions = true,
    version = "0.0.0",
    description = "CLI para orquestar el CI y el CD",
    subcommands = [GitCli, ShellCli, LoggerCli, ApkCli, AsdfCli]
)
class CLI implements Runnable {

    @Override
    void run(){}

    static void main(String[] args) {
        int exitCode = new CommandLine(new CLI()).execute(args)
        System.exit(exitCode)
    }
}
