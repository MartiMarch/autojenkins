package org.cicdcli.apk

import org.cicdcli.shell.po.ShellOutput
import org.cicdcli.apk.po.ApkPackage
import org.cicdcli.logger.Logger
import org.cicdcli.shell.Shell


class Apk {

    static Map<String, ApkPackage> listPackages() {
        Map<String, ApkPackage> pacakges = [:]
        List<String> lines
        ShellOutput so

        so = Shell.exec('apk list --installed --cache-dir $PATH_APK_CACHE')
        Shell.checkShellError(so)

        lines = so.output.readLines()
        lines.each { String line ->
            List<String> lineTokens = line.replaceAll('[\\[\\]{}]', '').split(' ')

            if(lineTokens.size() == 5) {
                String packageName = lineTokens[2]
                String packageOrg = lineTokens[3].substring(1, lineTokens[3].size()-1)

                if(!pacakges.containsKey(packageName)) {
                    pacakges[packageName] = new ApkPackage(
                        name: packageName,
                        version: getPackageVersion(packageName),
                        organization: packageOrg,
                        collection: []
                    )
                }
                pacakges[packageName].collection.add(lineTokens[0])
            }
        }

        return pacakges
    }

    static String getPackageVersion(String packageName) {
        String versionLineRegex = ~/^.*=.*/
        String version = 'unknown'
        List<String> lines

        ShellOutput so = Shell.exec("apk version ${packageName}")
        Shell.checkShellError(so)

        lines = so.output.tokenize('\n')
        lines.each { String line ->
            if(line.matches(versionLineRegex)) {
                version = line.replaceAll(' ', '').tokenize('=').last()
            }
        }

        return version
    }

    static addPackage(String packageName, String packageVersion = '') {
        if(packageVersion.isEmpty()) {
            ShellOutput so = Shell.exec("apk add --cache-dir \$PATH_APK_CACHE ${packageName}")
            Shell.checkShellError(so)
            Logger.info("Apk package ${packageName} added")
        } else {
            ShellOutput so = Shell.exec("apk add --cache-dir \$PATH_APK_CACHE ${packageName}=${packageVersion}")
            Shell.checkShellError(so)
            Logger.info("Apk package ${packageName}=${packageVersion} added")
        }
    }

    static deletePackage(String packageName) {
        ShellOutput so = Shell.exec("apk del --cache-dir \$PATH_APK_CACHE ${packageName}")
        Shell.checkShellError(so)
    }
}
