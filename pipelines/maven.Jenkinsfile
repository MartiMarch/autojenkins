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
        stage('Owsap') {
            when {
                expression { return isPR() }
            }
            steps {
                cicdcli('maven owasp')
            }
        }
        */
        stage('Build') {
            when {
                expression { return isPushMaster() }
            }
            steps {
                script {
                    cicdcli('maven updateVersion "pom.xml"')
                    String name = cicdcli('maven name "pom.xml"')
                    String version = cicdcli('maven version "pom.xml"')

                    sh("docker build -f Dockerfile -t \$DOCKER_HUB_REPO/${name}:${version} . ")
                    sh('docker loign -u $DOCKER_HUB_USER -p $DOCKER_HUB_PASSWORD')
                    sh("docker push \$DOCKER_HUB_REPO/${name}:${version}")
                }
            }
        }
        stage('Publish') {
            when {
                expression { return isPushMaster() }
            }
            steps {
                cicdcli('maven publish')
                //TODO: publicar en Nexus, un build con docker, analizar con rivy y hadolint y un rico push
            }
        }
    }
}


String cicdcli(String command){
    String output = sh(script: "sudo -E java -jar /opt/cache/cicdcli/cicdcli.jar ${command}", returnStdout: true)
    println(output)

    return output
}

boolean isPushMaster() {
    return cicdcli('release isMasterToMaster "."') == 'true'
}

boolean isPR() {
    return cicdcli('release isMasterPR "."') == 'true'
}
