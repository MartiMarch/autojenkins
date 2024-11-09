package org.cicdcli.logger

import picocli.CommandLine.Parameters
import picocli.CommandLine.Command


@Command(
    name = "logger",
    description = "Interact with cli logger",
    mixinStandardHelpOptions = true,
    subcommands = [Info, Error, Debug, Warning, Core]
)
class LoggerCli implements Runnable {

    @Command(
        name = "info",
        description = "Prints text wit info logger"
    )
    static class Info implements Runnable {

        @Parameters(
            index = "0",
            description = "Info logger print"
        )
        String message

        @Override
        void run(){
            Logger.info(message)
        }
    }

    @Command(
        name = "error",
        description = "Prints text wit error logger"
    )
    static class Error implements Runnable {

        @Parameters(
            index = "0",
            description = "Error logger print"
        )
        String message

        @Override
        void run(){
            Logger.error(message)
        }
    }

    @Command(
        name = "debug",
        description = "Prints text wit debug logger"
    )
    static class Debug implements Runnable {

        @Parameters(
            index = "0",
            description = "Debug  logger print"
        )
        String message

        @Override
        void run(){
            Logger.debug(message)
        }
    }

    @Command(
        name = "warning",
        description = "Prints text wit debug logger"
    )
    static class Warning implements Runnable {

        @Parameters(
            index = "0",
            description = "Warning  logger print"
        )
        String message

        @Override
        void run(){
            Logger.warning(message)
        }
    }

    @Command(
        name = "core",
        description = "Prints text wit debug logger"
    )
    static class Core implements Runnable {

        @Parameters(
            index = "0",
            description = "Core  logger print"
        )
        String message

        @Override
        void run(){
            Logger.core(message)
        }
    }

    @Override
    void run(){}

}
