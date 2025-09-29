package org.cicdcli.logger

class Logger {

    static private Map<String, String> ANSI_COLORS = [
        yellow: '\033[33m',
        blue: '\033[34m',
        green: '\033[32m',
        red: '\033[31m',
        magenta: '\033[35m',
    ]

    static void info(String message) {
        println("${ANSI_COLORS.blue}${message}")
    }

    static void warning(String message) {
        println("${ANSI_COLORS.yellow}${message}")
    }

    static void debug(String message) {
        if(LoggerConf.logLevel() >= 1) {
            println("${ANSI_COLORS.magenta}${message}")
        }
    }

    static void core(String message) {
        if(LoggerConf.logLevel() >= 2) {
            println("${ANSI_COLORS.green}${message}")
        }
    }

    static void error(String message){
        println("${ANSI_COLORS.red}${message}")
    }
}
