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
                    sh 'echo "Hello World"'
                    sh 'sleep 99999'
                }
            }
        }
    }
}

