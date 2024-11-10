package org.cicdcli.logger

import spock.lang.Specification
import spock.lang.Unroll


class LoggerTest extends Specification {

    @Unroll
    def '#logLevel function must print information with label and ANSI #logLevel log level'() {
        given:
        GroovyMock(LoggerConf, global: true)
        LoggerConf.logLevel() >> 3
        def (shelLogs, originalShellLogs) = capturedLogs()

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

    @Unroll
    def 'debug must print the message because LoggerConf.logLevel() is higher or equal than #logLevel'() {
        given:
        GroovyMock(LoggerConf, global: true)
        LoggerConf.logLevel() >> logLevel
        def (shelLogs, originalShellLogs) = capturedLogs()

        when:
        Logger.debug('Debug message')

        then:
        shelLogs.toString('UTF-8') == '\033[35mDebug message\n'

        cleanup:
        System.setOut(originalShellLogs)

        where:
        logLevel << [1, 20, 2]
    }

    @Unroll
    def "debug musn't print the message because LoggerConf.logLevel() is higher or equal than #logLevel"() {
        given:
        GroovyMock(LoggerConf, global: true)
        LoggerConf.logLevel() >> logLevel
        def (shelLogs, originalShellLogs) = capturedLogs()

        when:
        Logger.debug('Debug message')

        then:
        !shelLogs.toString('UTF-8').contains('\033[35mDebug message\n')

        cleanup:
        System.setOut(originalShellLogs)

        where:
        logLevel << [0, null, -30]
    }

    @Unroll
    def 'core must print the message because LoggerConf.logLevel() is higher or equal than #logLevel'() {
        given:
        GroovyMock(LoggerConf, global: true)
        LoggerConf.logLevel() >> logLevel
        def (shelLogs, originalShellLogs) = capturedLogs()

        when:
        Logger.core('Core message')

        then:
        shelLogs.toString('UTF-8') == '\033[32mCore message\n'

        cleanup:
        System.setOut(originalShellLogs)

        where:
        logLevel << [2, 20, 1000]
    }

    @Unroll
    def "core musn't print the message because LoggerConf.logLevel() is higher or equal than #logLevel"() {
        given:
        GroovyMock(LoggerConf, global: true)
        LoggerConf.logLevel() >> logLevel
        def (shelLogs, originalShellLogs) = capturedLogs()

        when:
        Logger.core('Core message')

        then:
        !shelLogs.toString('UTF-8').contains('\033[35mCore message\n')

        cleanup:
        System.setOut(originalShellLogs)

        where:
        logLevel << [0, 1, -2]
    }


    Tuple2<ByteArrayOutputStream, PrintStream> capturedLogs() {
        ByteArrayOutputStream shellLogs = new ByteArrayOutputStream()
        PrintStream originalShellLogs = System.out
        System.setOut(new PrintStream(shellLogs))

        return new Tuple2<>(shellLogs, originalShellLogs)
    }
}
