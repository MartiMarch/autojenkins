package org.cicdcli.apk

import org.cicdcli.shell.po.ShellOutput
import org.cicdcli.apk.po.ApkPackage
import org.cicdcli.logger.Logger
import org.cicdcli.shell.Shell


class Apk {

    static private Map<String, ApkPackage> listPackages() {
        Map<String, ApkPackage> pacakges = [:]
        ShellOutput so
        String lines

        so = Shell.exec("apk list --installed")
        checkShellError(so)

        lines = so.output.tokenize('\n')
        lines.each{ String line ->
            List<String> lineTokens = line.tokenize(' ')
            if(lineTokens.size() == 4){
                String packageName = lineTokens[2].substring(1, lineTokens[2].size()-1)
                String packageOrg = lineTokens[3].substring(1, lineTokens[3].size()-1)

                if(!pacakges.containsKey(packageName)){
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

    static private String getPackageVersion(String packageName) {
        String versionLineRegex = ~/^.*=.*/
        String version = 'unknown'
        List<String> lines
        ShellOutput so

        so = Shell.exec("apk version ${packageName}")
        checkShellError(so)

        lines = so.output.tokenize('\n')
        lines.each { String line ->
            if(line.matches(versionLineRegex)) {
                version = line.replaceAll(' ', '').tokenize('=').last()
            }
        }

        return version
    }

    static private void checkShellError(ShellOutput so){
        if(so.isError){
            Logger.error(so.error)
            throw new Exception(so.error)
        }
    }
}
