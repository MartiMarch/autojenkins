package org.cicdcli.maven

import org.cicdcli.apk.Apk
import org.cicdcli.shell.Shell
import org.cicdcli.logger.Logger
import org.cicdcli.shell.po.ShellOutput


class Maven {

    static void initialize() {
        Apk.addPackage("maven")
        Logger.info("Maven installed!")
    }

    static void lint() {
        ShellOutput so = Shell.exec('mvn checkstyle:check')
        Shell.checkShellError(so)
        Logger.info(so.output)
    }

    static void test() {
        ShellOutput so = Shell.exec('mvn test')
        Shell.checkShellError(so)
        Logger.info(so.output)
    }

    static void owasp() {
        ShellOutput so = Shell.exec('mvn org.owasp:dependency-check-maven:check')
        Shell.checkShellError(so)
        Logger.info(so.output)
    }

    static void build() {
        ShellOutput so = Shell.exec('mvn clean package')
        Shell.checkShellError(so)
        Logger.info(so.output)
    }

    static void publish() {
        String settingsPath = MavenConf.getSettingsPath()
        ShellOutput so = Shell.exec("mvn deploy -s ${settingsPath} -DskipTests")
        Shell.checkShellError(so)
        Logger.info(so.output)
    }
}
