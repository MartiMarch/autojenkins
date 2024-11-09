package org.cicdcli.logger

import spock.lang.Specification
import spock.lang.Unroll

"""
class LoggerTest extends Specification {

    
    LoggerConf conf = Mock()

    @Unroll
    def '#logLevel function must print information with label and ANSI #logLevel log level' (){
        given:
        def (shelLogs, originalShellLogs) = capturedLogs()
        conf.logLevel() >> 3

        when:
        Logger."${logLevel}"(inputMessage)

        then:
        shelLogs.toString('UTF-8') == expectedMessage

        cleanup:
        System.setOut(originalShellLogs)

        where:
        logLevel  | inputMessage           | expectedMessage
        'info'    | 'Info fake message'    | '\033[34mInfo fake message\n'
        'warning' | 'Warning fake message' | '\033[33mWarning fake message\n'
        'debug'   | 'Debug fake message'   | '\033[35mDebug fake message\n'
        'core'    | 'Core fake message'    | '\033[32mCore fake message\n'
        'error'   | 'Error fake message'   | '\033[31mError fake message\n'
    }

    Tuple2<ByteArrayOutputStream, PrintStream> capturedLogs() {
        ByteArrayOutputStream shellLogs = new ByteArrayOutputStream()
        PrintStream originalShellLogs = System.out
        System.setOut(new PrintStream(shellLogs))

        return new Tuple2<>(shellLogs, originalShellLogs)
    }
}
"""
