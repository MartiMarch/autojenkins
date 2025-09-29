## JENKINS
helm repo add jenkins https://charts.jenkins.io
# helm search repo jenkins --versions

## NEXUS SONATYPE
helm repo add sonatype https://sonatype.github.io/helm3-charts/
#helm search repo sonatype --versions

## GITEA
helm repo add gitea-charts https://dl.gitea.com/charts/
#helm search repo gitea --versions

## SONARQUBE
helm repo add sonarqube https://SonarSource.github.io/helm-chart-sonarqube
#helm search repo sonar --versions

## ¿?
helm repo add curiedfcharts https://curie-data-factory.github.io/helm-charts/

helm dep update
helm repo update
helm dependency build
helm -n autojenkins delete autojenkins
sleep 10
kubectl -n autojenkins delete pvc --all
sleep 30
kubectl -n autojenkins delete pv --all
sleep 10
helm -n autojenkins install autojenkins -f values.yaml .
