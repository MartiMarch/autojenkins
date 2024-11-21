pipeline {
    agent {
        kubernetes {
            inheritFrom 'generic-agent'
            yamlMergeStrategy merge()
            defaultContainer 'generic-agent'
        }
    }
    options {
        buildDiscarder(logRotator(
            numToKeepStr: '5',
            artifactNumToKeepStr: '5'
        ))
        disableConcurrentBuilds()
    }
    stages {
        stage('Initalize') {
            steps {
                script {
                    sh("export CLEAR_CACHE_PATHS='yes'")
                    sh('sudo -E bash /init-container-script.sh')
                    cicdcli('apk add "maven"')
                    cicdcli('apk add "docker"')
                }
            }
        }
        stage('Lint') {
            when {
                expression { return isPR() }
            }
            steps {
                cicdcli('maven lint')
            }
        }
        stage('Test') {
            when {
                expression { return isPR() }
            }
            steps {
                cicdcli('maven test')
            }
        }
        /* Esta cosa es demasiado lenta...
        stage('Scan') {
            when {
                expression { return isPR() }
            }
            steps {
                //cicdcli('maven owasp')
                // Meter aquilo de trivy y hadolint
            }
        }
        */
        stage('Build') {
            when {
                expression { return isPushMaster() }
            }
            steps {
                script {
                    cicdcli('maven updateVersion "." "pom.xml"')
                    String name = cicdcli('maven name "pom.xml"')
                    String version = cicdcli('maven version "pom.xml"')

                    cicdcli('maven build')
                    cicdcli("docker build ${name} ${version}")
                }
            }
        }
        stage('Publish') {
            when {
                expression { return isPushMaster() }
            }
            steps {
                script {
                    String name = cicdcli('maven name "pom.xml"')
                    String version = cicdcli('maven version "pom.xml"')

                    cicdcli('maven publish "pom.xml"')
                    cicdcli("docker login")
                    cicdcli("docker push '${name}' '${version}'")
                }
            }
        }
    }
}


String cicdcli(String command, boolean mustPrint = true){
    String output = sh(script: "sudo -E java -jar /opt/cache/cicdcli/cicdcli.jar ${command}", returnStdout: true)
    if(mustPrint)
        println(output)

    return output
}

boolean isPushMaster() {
    return cicdcli('release isMasterToMaster "."', false) == 'true'
}

boolean isPR() {
    return cicdcli('release isMasterPR "."', false) == 'true'
}
