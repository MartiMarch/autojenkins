package org.cicdcli.shell

import org.cicdcli.shell.po.ShellOutput
import org.cicdcli.logger.Logger
import picocli.CommandLine.Parameters
import picocli.CommandLine.Command


@Command(
    name = "shell",
    description = "Interact with linux shell",
    mixinStandardHelpOptions = true,
    subcommands = [Exec]
)
class ShellCli implements Runnable {

    @Command(
        name = "exec",
        description = "Executes desired Linux command"
    )
    static class Exec implements Runnable {

        @Parameters(
            index = "0",
            description = "Command top be executed"
        )
        String command

        @Override
        void run() {
            ShellOutput so = Shell.exec(command)
            if(so.isError){
                Logger.error(so.error)
                throw new Exception(so.error)
            } else {
                Logger.info(so.output)
            }
        }
    }

    @Override
    void run() {}
}
