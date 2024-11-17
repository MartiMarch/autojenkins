FROM alpine:3.20.3

##################
# Permissions
##################
USER root
RUN apk add --no-cache sudo
RUN adduser -D -h /home/jenkins -s /bin/bash jenkins
RUN echo "jenkins ALL=(ALL) NOPASSWD: ALL" >> /etc/sudoers
RUN chown -R jenkins:jenkins /opt
USER jenkins

##################
# Envs
##################
## Cache
ENV PATH_APK_CACHE='/opt/cache/apk'
ENV PATH_ASDF_CACHE='/opt/cache/asdf'
ENV PATH_JENKINS_CACHE='/opt/cache/jenkins'
ENV PATH_CICDCLI_CACHE='/opt/cache/cicdcli'
## Asdf
ENV ASDF_DATA_DIR='/opt/cache/asdf/.asdf'
ENV ASDF_DIR='/opt/cache/asdf/.asdf'
## Nexus
# Todo lo de Nexus Reemlazar en entorno productivo
ENV NEXUS_USER='admin'
ENV NEXUS_PASSWORD='1234'
ENV NEXUS_HTTP_PROTOCOL='http'
ENV NEXUS_DOMAIN='autojenkins-nexus.autojenkins.svc.cluster.local'
ENV NEXUS_PORT='8081'
ENV NEXUS_MVN_REPOSITORY='autojenkins'
ENV NEXUS_MVN_CICDCLI_JAR_SUBPATH='org/cicdcli/cicdcli/0.0.0/cicdcli-0.0.0-all.jar'

##################
# Cache paths
##################
RUN mkdir -p $PATH_APK_CACHE
RUN mkdir -p $PATH_ASDF_CACHE
RUN mkdir -p $PATH_ASDF_CACHE/.asdf/*
RUN mkdir -p $PATH_ASDF_CACHE/plugins/*
RUN mkdir -p $PATH_JENKINS_CACHE
RUN mkdir -p $PATH_CICDCLI_CACHE

##################
# Apk cache configuration
##################
RUN sudo apk add --no-cache bash

##################
# Init containers script
##################
COPY init-container-script.sh /init-container-script.sh

CMD ["bash"]
