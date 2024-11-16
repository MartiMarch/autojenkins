package org.cicdcli.shell

import org.cicdcli.logger.Logger
import org.cicdcli.shell.po.ShellOutput


class Shell {

    static ShellOutput exec(String command) {
        Process shell = command.execute()
        int exitCode = shell.waitFor()

        return new ShellOutput(
            output: shell.in.text,
            error: shell.err.text,
            isError: exitCode != 0
        )
    }

    static void checkShellError(ShellOutput so) {
        if(so.isError) {
            Logger.error(so.error)
            throw new Exception(so.error)
        }
    }
}
