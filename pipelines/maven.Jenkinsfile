pipeline {
    agent {
        kubernetes {
            inheritFrom 'generic-agent',
            yamlMergeStrategy merge()
        }
    }
    stages {
        stage('Hello World') {
            steps {
                container('generic-agent') {
                    sh 'echo "Hello World"'
                }
            }
        }
    }
}