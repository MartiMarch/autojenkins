package org.cicdcli.maven

import org.cicdcli._helpers.Xml
import org.cicdcli.apk.Apk
import org.cicdcli.release.Release
import org.cicdcli.shell.Shell
import org.cicdcli.logger.Logger
import org.cicdcli.shell.po.ShellOutput


class Maven {

    static void initialize() {
        Apk.addPackage("maven")
        Logger.info("Maven installed!")
    }

    static void lint() {
        ShellOutput so = Shell.exec('mvn -B checkstyle:check')
        Shell.checkShellError(so)
        Logger.info(so.output)
    }

    static void test() {
        ShellOutput so = Shell.exec('mvn -B test')
        Shell.checkShellError(so)
        Logger.info(so.output)
    }

    static void owasp() {
        ShellOutput so = Shell.exec('mvn -B org.owasp:dependency-check-maven:check')
        Shell.checkShellError(so)
        Logger.info(so.output)
    }

    static void build() {
        ShellOutput so = Shell.exec('mvn -B clean package -DskipTests')
        Shell.checkShellError(so)
        Logger.info(so.output)
    }

    static void publish(String pomPath, boolean isHttps = false) {
        String name = name(pomPath)
        String version = version(pomPath)
        String jarPath = "target/${name}-${version}.jar"
        String nexusDomain = MavenConf.getNexusDomain()
        String nexusPort = MavenConf.getNexusPort()
        String nexusRepo = MavenConf.getRepository()
        String nexusHttpProtocol = isHttps ? 'https' : 'http'
        String settingsRepo = Xml.castXml(MavenConf.getSettingsPath())?.servers?.server?.id

        ShellOutput so = Shell.exec(
            "mvn deploy:deploy-file -s ${MavenConf.getSettingsPath()}"
            + " -Dfile=${jarPath}"
            + " -DpomFile=${pomPath}"
            + " -Dpackaging=jar"
            + " -Durl=${nexusHttpProtocol}://${nexusDomain}:${nexusPort}/repository/${nexusRepo}/"
            + " -DrepositoryId=${settingsRepo}"
        )
        Shell.checkShellError(so)
        Logger.info(so.output)
    }

    static String name(String pomPath) {
        return Xml.castXml(pomPath)?.artifactId
    }

    static String version(String pomPath) {
        return Xml.castXml(pomPath)?.version
    }

    static void updateVersion(String repositoryPath, String pomPath) {
        String nextVersion = Release.nextVersion(repositoryPath, version(pomPath))
        ShellOutput so = Shell.exec("mvn -B versions:set -DnewVersion=${nextVersion}")
        Shell.checkShellError(so)

        Logger.info("Project updated to version ${nextVersion}")
    }
}
