FROM alpine:3.20.3
USER root

##################
# Envs
##################
ENV PATH_APK_CACHE='/opt/cache/apk'
ENV PATH_ASDF_CACHE='/opt/cache/asdf'
ENV PATH_JENKINS_CACHE='/opt/cache/jenkins'
ENV PATH_CICDCLI_CACHE='/opt/cache/cicdcli'

ENV ASDF_DATA_DIR='/opt/cache/asdf/.asdf'
ENV ASDF_DIR='/opt/cache/asdf/.asdf'

# Todo lo de Nexus Reemlazar en entorno productivo
ENV NEXUS_USER='admin'
ENV NEXUS_PASSWORD='1234'
ENV NEXUS_HTTP_PROTOCOL='http'
ENV NEXUS_DOMAIN='localhost'
ENV NEXUS_PORT='3333'
ENV NEXUS_MVN_REPOSITORY='autojenkins'
ENV NEXUS_MVN_CICDCLI_JAR_SUBPATH='org/example/untitled/1.0-SNAPSHOT/untitled-1.0-20241111.232444-3-all.jar'


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
RUN apk add --no-cache bash

##################
# Init containers script
##################
COPY init-container-script.sh /init-container-script.sh

CMD ["bash"]
