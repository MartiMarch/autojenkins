package org.cicdcli.apk

import org.cicdcli.shell.po.ShellOutput
import org.cicdcli.logger.Logger
import spock.lang.Specification
import org.cicdcli.shell.Shell


class ApkTest extends Specification {

    def 'getPackageVersion() must return expected apk package version'() {
        given:
        GroovyMock(Shell, global: true)
        String packageName = 'fake-package-name'
        Shell.exec("apk version ${packageName}") >> new ShellOutput(
            output: giveValidPackageVersion(),
            error: '',
            isError: false
        )

        when:
        String version = Apk.getPackageVersion(packageName)

        then:
        noExceptionThrown()
        version == '2.45.2-r0'
    }

    def 'getPackageVersion() should rise an error because Shell.exec() return an error'() {
        given:
        GroovyMock(Shell, global: true)
        GroovyMock(Logger, global: true)

        String packageName = 'fake-package-name'
        String errorMsg = 'fake error'
        Shell.checkShellError(_) >> {
            throw new Exception(errorMsg)
        }
        Shell.exec("apk version ${packageName}") >> new ShellOutput(
            output: 'this shell output no matters',
            error: errorMsg,
            isError: true
        )

        when:
        Apk.getPackageVersion(packageName)

        then:
        Exception exc = thrown(Exception)
        exc.message == errorMsg
    }

    String giveValidPackageVersion(){
        return 'Installed:                                Available:\n' +
               'git-2.45.2-r0                           = 2.45.2-r0\n'
    }
}
