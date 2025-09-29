package org.cicdcli.asdf

import org.cicdcli.logger.Logger
import picocli.CommandLine.Parameters
import picocli.CommandLine.Option
import picocli.CommandLine


@CommandLine.Command(
    name = "asdf",
    description = "Interact with asdf",
    mixinStandardHelpOptions = true,
    subcommands = [AddPlugin, DeletePlugin, ListPlugins, Install, Global]
)
class AsdfCli {

    @CommandLine.Command(
        name = "deletePlugin",
        mixinStandardHelpOptions = true,
        description = "Delete asdf plugin"
    )
    static class DeletePlugin implements Runnable  {

        @Parameters(
            index = "0",
            description = "Plugin name"
        )
        String pluginName

        @Override
        void run() {
            Asdf.deletePlugin(pluginName)
        }
    }

    @CommandLine.Command(
        name = "addPlugin",
        mixinStandardHelpOptions = true,
        description = "Add asdf plugin"
    )
    static class AddPlugin implements Runnable {

        @Parameters(
            index = "0",
            description = "Plugin name"
        )
        String pluginName

        @Option(
            names = ["--PR", "--pluginRepository"],
            description = "Plugin repository or path"
        )
        String pluginRepository

        @Override
        void run(){
            Asdf.addPlugin(pluginName, pluginRepository)
        }
    }

    @CommandLine.Command(
        name = "listPlugins",
        mixinStandardHelpOptions = true,
        description = "Return asdf plugin list"
    )
    static class ListPlugins implements Runnable {

        @Override
        void run() {
            Logger.info(Asdf.listPlugins())
        }
    }

    @CommandLine.Command(
        name = "install",
        mixinStandardHelpOptions = true,
        description = "Install asdf tool with specific version"
    )
    static class Install implements Runnable {

        @Parameters(
            index = "0",
            description = "Asdf tool name (plugin name)"
        )
        String toolName

        @Parameters(
            index = "1",
            description = "Asdf tool version"
        )
        String toolVersion

        @Override
        void run(){
            Asdf.install(toolName, toolVersion)
        }
    }

    @CommandLine.Command(
        name = "global",
        mixinStandardHelpOptions = true,
        description = ""
    )
    static class Global implements Runnable {

        @Parameters(
            index = "0",
            description = "Asdf tool name (plugin name)"
        )
        String toolName

        @Parameters(
            index = "1",
            description = "Asdf tool version"
        )
        String toolVersion

        @Override
        void run() {
            Asdf.global(toolName, toolVersion)
        }
    }
}
