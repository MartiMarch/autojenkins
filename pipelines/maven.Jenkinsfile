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
                    initialize()
                    cicdcli('apk add "maven"')
                }
            }
        }
        stage('Lint') {
            steps {
                container('generic-agent') {
                    sh('mvn checkstyle:check')
                }
            }
        }
        stage('Test') {
            steps {
                container('generic-agent') {
                    sh('mvn test')
                }
            }
        }
        /* Esta cosa es demasiado lenta...
        stage('Owsap') {
            steps {
                container('generic-agent') {
                    sh('mvn org.owasp:dependency-check-maven:check')
                }
            }
        }
        */
        stage('Build') {
            steps {
                container('generic-agent') {
                    sh('mvn clean package')
                }
            }
        }
        stage('Puiblish') {
            steps {
                container('generic-agent') {
                    writeSettings()
                    sh('mvn deploy -s /opt/settings.xml -DskipTests')
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

/*
* Esta chapuza se ha de cargar desde un ConfigMap desde el agente
*/
void writeSettings(){
    writeFile file: 'settings.xml', text:
'''
<settings>
    <servers>
        <server>
            <id>nexus-releases</id>
            <username>admin</username>
            <password>1234</password>
        </server>
    </servers>
</settings>
'''
}
