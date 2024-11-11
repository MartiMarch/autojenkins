package org.cicdcli.apk

import org.cicdcli.apk.po.ApkPackage
import org.cicdcli.logger.Logger
import picocli.CommandLine


@CommandLine.Command(
    name = "apk",
    description = "Interact with apk",
    mixinStandardHelpOptions = true,
    subcommands = [List]
)
class ApkCli implements Runnable{

    @CommandLine.Command(
        name = "list",
        description = "Return apk packages"
    )
    static class List implements Runnable {

        @Override
        void run() {
            Map<String, ApkPackage> packages = Apk.listPackages()

            Logger.info('Installed pacakges:')
            packages.each { ApkPackage pacakge ->
                Logger.info(pacakge.name)
            }
        }
    }

    @Override
    void run() {}
}
