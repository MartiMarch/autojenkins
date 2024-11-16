package org.cicdcli.asdf

import org.cicdcli.shell.po.ShellOutput
import org.cicdcli.logger.Logger
import org.cicdcli.shell.Shell


class Asdf {

    static void addPlugin(String pluginName, String pluginRepository = null) {
        ShellOutput so

        if(pluginRepository == null) {
            so = Shell.exec("asdf plugin-add ${pluginName}")
            Shell.checkShellError(so)
            Logger.info("Asdf plugin ${pluginName} added")
        } else {
            so = Shell.exec("asdf plugin-add ${pluginName} ${pluginRepository}")
            Shell.checkShellError(so)
            Logger.info("Asdf plugin ${pluginName} (${pluginRepository}) added")
        }
    }

    static void deletePlugin(String pluginName) {
        ShellOutput so = Shell.exec("asdf plugin-remove ${pluginName}")
        Shell.checkShellError(so)
        Logger.info("asdf plugin ${pluginName} removed")
    }

    static String listPlugins() {
        ShellOutput so = Shell.exec("asdf plugin list")
        Shell.checkShellError(so)

        return so.output
    }

    static void install(String pluginName, String version) {
        ShellOutput so = Shell.exec("asdf install ${pluginName} ${version}")
        Shell.checkShellError(so)
        Logger.info("Asdf plugin ${pluginName} ${version} installed")
    }

    static void global(String pluginName, String version){
        ShellOutput so = Shell.exec("asdf global ${pluginName} ${version}")
        Shell.checkShellError(so)
        Logger.info("Asdf plugin ${pluginName} set to ${version} version in global system")
    }
}
