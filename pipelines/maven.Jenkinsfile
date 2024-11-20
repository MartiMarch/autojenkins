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
                        initialize()
                        cicdcli('apk add "maven"')

                        if(isPushMaster()){
                            cicdcli('apk add "docker"')
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
                            cicdcli('maven lint')
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
                            dcli('maven test')
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
                            dcli('maven owasp')
                        }
                    }
                }
            }
        }
        */
        stage('Build') {
            steps {
                container('generic-agent') {
                    script {
                        if(isPushMaster()) {
                            cicdcli('maven build')
                            cicdcli('apk add "docker"')
                            cicdcli('maven updateVersion "pom.xml"')
                            String version = cicdcli('maven version "pom.xml"')
                            String projectName = cicdcli('maven name "pom.xml"')
                            //sh("docker build -f Dockerfile -t ${projectName}:${nextVersion} . ")
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
                            cicdcli('maven publish')
                        }
                    }
                }
            }
        }
        //TODO: publicar en Nexus, un build con docker, analizar con rivy y hadolint y un rico push
    }
}


void initialize(){
    sh("export CLEAR_CACHE_PATHS='yes'")
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
