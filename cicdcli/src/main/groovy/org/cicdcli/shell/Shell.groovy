package org.cicdcli.shell

import org.cicdcli.logger.Logger
import org.cicdcli.shell.po.ShellOutput


class Shell {

    static ShellOutput exec(String command) {
        Process shell = command.execute()

        BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(shell.inputStream))
        BufferedReader stderrReader = new BufferedReader(new InputStreamReader(shell.errorStream))
        StringBuilder output = new StringBuilder()
        StringBuilder error = new StringBuilder()

        Thread stdoutThread = new Thread({
            String line
            while ((line = stdoutReader.readLine()) != null) {
                output.append(line).append('\n')
            }
        })
        Thread stderrThread = new Thread({
            String line
            while ((line = stderrReader.readLine()) != null) {
                error.append(line).append('\n')
            }
        })

        stdoutThread.start()
        stderrThread.start()
        int exitCode = shell.waitFor()
        stdoutThread.join()
        stderrThread.join()
        stdoutReader.close()
        stderrReader.close()

        return new ShellOutput(
            output: output.toString(),
            error: error.toString(),
            isError: exitCode != 0
        )
    }

    static void checkShellError(ShellOutput so) {
        if (so.isError) {
            Logger.error(so.error)
            throw new Exception(so.error)
        }
    }
}