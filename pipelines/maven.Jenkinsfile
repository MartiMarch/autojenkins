pipeline {
    agent {
        kubernetes {
            inheritFrom 'generic-agent'
            yamlMergeStrategy merge()
            yaml '''
              apiVersion: v1
              kind: Pod
              spec:
                containers:
                - name: generic-agent
                  env:
                  - name: NEXUS_DOMAIN
                    value: "autojenkins-nexus.autojenkins.svc.cluster.local"
                  - name: NEXUS_PORT
                    value: "8081"
            '''
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
