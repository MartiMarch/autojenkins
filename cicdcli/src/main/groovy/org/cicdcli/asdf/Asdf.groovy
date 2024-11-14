package org.cicdcli.asdf

import org.cicdcli.shell.po.ShellOutput
import org.cicdcli.logger.Logger
import org.cicdcli.shell.Shell


class Asdf {

    static void addPlugin(String pluginName, String pluginRepository = null) {
        ShellOutput so

        if(pluginRepository == null) {
            so = Shell.exec("asdf plugin-add ${pluginName}")
            checkShellError(so)

            Logger.info("Asdf plugin ${pluginName} added")
        } else {
            so = Shell.exec("asdf plugin-add ${pluginName} ${pluginRepository}")
            checkShellError(so)

            Logger.info("Asdf plugin ${pluginName} (${pluginRepository}) added")
        }
    }

    static void deletePlugin(String pluginName) {
        ShellOutput so = Shell.exec("asdf plugin-remove ${pluginName}")
        checkShellError(so)

        Logger.info("asdf plugin ${pluginName} removed")
    }

    static String listPlugins() {
        ShellOutput so = Shell.exec("asdf plugin list")
        checkShellError(so)

        return so.output
    }

    static void install(String pluginName, String version) {
        ShellOutput so = Shell.exec("asdf install ${pluginName} ${version}")
        checkShellError(so)

        Logger.info("Asdf plugin ${pluginName} ${version} installed")
    }

    static void global(String pluginName, String version){
        ShellOutput so = Shell.exec("asdf global ${pluginName} ${version}")
        checkShellError(so)

        Logger.info("Asdf plugin ${pluginName} set to ${version} version in global system")
    }

    static private void checkShellError(ShellOutput so) {
        if(so.isError) {
            Logger.error(so.error)
            throw new Exception(so.error)
        }
    }
}
