pipeline {
    agent {
        kubernetes {
            inheritFrom 'generic-agent'
            yamlMergeStrategy merge()
        }
    }
    stages {
        stage('Pìpeline') {
            steps {
                container('generic-agent') {
                    sh 'sudo -E bash /init-container-script.sh'
                    sh 'echo "Hello World"'
                    sh 'java -jar /opt/cache/cicdcli/ --help'
                }
            }
        }
    }
}
