package org.cicdcli.logger

import org.cicdcli._helpers.po.ConfItem
import org.cicdcli._helpers.Envs
import spock.lang.Specification
import spock.lang.Unroll


class LoggerConfTest extends Specification {

    @Unroll
    def 'logLevel() must read environment variable and set expected value #expectedLogLevel'() {
        given:
        GroovyMock(Envs, global: true)
        Envs.getEnv('log.level', false, Integer) >> new ConfItem(
            isSecret: false,
            key: 'log.level',
            value: mockedLogLevel,
            type: Integer
        )

        when:
        Integer logLevel = LoggerConf.logLevel()

        then:
        logLevel == expectedLogLevel

        where:
        expectedLogLevel | mockedLogLevel
        0                | 0
        1                | 1
        2                | 2
        -1               | -1
    }
}
