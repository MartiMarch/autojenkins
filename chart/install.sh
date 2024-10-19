helm repo add jenkins https://charts.jenkins.io
# helm search repo jenkins --versions
helm repo add sonatype https://sonatype.github.io/helm3-charts/
# helm search repo sonatype/nexus-repository-manager --versions
helm repo update
rm -rf Chart.lock
helm dependency build
helm -n autojenkins delete autojenkins
sleep 30
helm -n autojenkins install autojenkins -f values.yaml .
