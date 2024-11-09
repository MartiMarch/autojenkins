package shell

import org.cicdcli._helpers.po.ShellOutput


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

}
