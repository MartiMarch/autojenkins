package org.cicdcli

import org.cicdcli.apk.ApkCli
import picocli.CommandLine.Command
import picocli.CommandLine
import org.cicdcli.logger.LoggerCli
import org.cicdcli.shell.ShellCli
import org.cicdcli.git.GitCli


@Command(
    name = "cicdcli",
    mixinStandardHelpOptions = true,
    version = "0.0.0",
    description = "CLI para orquestar el CI y el CD",
    subcommands = [GitCli, ShellCli, LoggerCli, ApkCli]
)
class CLI implements Runnable {

    @Override
    void run(){}

    static void main(String[] args) {
        int exitCode = new CommandLine(new CLI()).execute(args)
        System.exit(exitCode)
    }
}
