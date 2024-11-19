pipeline {
    agent {
        kubernetes {
            inheritFrom 'generic-agent'
            yamlMergeStrategy merge()
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
                container('generic-agent') {
                    script {
                        if(isPR()) {
                            initialize()
                            cicdcli('apk add "maven"')
                        }
                    }
                }
            }
        }
        stage('Lint') {
            steps {
                container('generic-agent') {
                    script {
                        if(isPR()) {
                            sh('mvn checkstyle:check')
                        }
                    }
                }
            }
        }
        stage('Test') {
            steps {
                container('generic-agent') {
                    script {
                        if(isPR()) {
                            sh('mvn test')
                        }
                    }
                }
            }
        }
        /* Esta cosa es demasiado lenta...
        stage('Owsap') {
            steps {
                container('generic-agent') {
                    script {
                        if(isPR()) {
                            sh('mvn org.owasp:dependency-check-maven:check')
                        }
                    }
                }
            }
        }
        */
        stage('Build') {
            stage('Maven build') {
                steps {
                    container('generic-agent') {
                        script {
                            if(isPushMaster()) {
                                sh('mvn clean package')
                                cicdcli('apk add "docker"')
                                sh('docker build -f Dockerfile -t ')
                            }
                        }
                    }
                }
            }
        }
        stage('Publish') {
            steps {
                container('generic-agent') {
                    script {
                        if(isPushMaster()) {
                            writeSettings()
                            sh('mvn deploy -s /opt/settings.xml -DskipTests')
                        }
                    }
                }
            }
        }
        //TODO: publicar en Nexus, un build con docker, analizar con rivy y hadolint y un rico push
    }
}


void initialize(){
    sh('sudo -E bash /init-container-script.sh')
}

String cicdcli(String command){
    String output = sh(script: "sudo -E java -jar /opt/cache/cicdcli/cicdcli.jar ${command}", returnStdout: true)
    println(output)

    return output
}


boolean isPushMaster() {
    String source = cicdcli('release source "."')
    String target = cicdcli('release target "."')

    return (
        source == 'master'
        &&
        target == 'master'
    )
}

boolean isPR() {
    String source = cicdcli('release source "."')
    String target = cicdcli('release target "."')

    return (
        source != 'master'
        &&
        target == 'master'
    )
}
