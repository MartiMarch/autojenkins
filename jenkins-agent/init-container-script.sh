if [[ $CLEAR_CACHE_PATHS == "yes" || $CLEAR_CACHE_PATHS == "true" ]]; then
    echo "Borrando contenido de los directorios de caché..."
    rm -rf $PATH_APK_CACHE
    rm -rf $PATH_ASDF_CACHE
    rm -rf $PATH_JENKINS_CACHE
    rm -rf $PATH_CICDCLI_CACHE

    echo "Creando directorios de la cache..."
    mkdir -p $PATH_APK_CACHE
    mkdir -p $PATH_ASDF_CACHE
    mkdir -p $PATH_ASDF_CACHE/plugins
    mkdir -p $PATH_JENKINS_CACHE
    mkdir -p $PATH_CICDCLI_CACHE

    echo "Instalando paquetes de APK..."
    sudo apk add --cache-dir $PATH_APK_CACHE curl
    sudo apk add --cache-dir $PATH_APK_CACHE git
    sudo apk add --cache-dir $PATH_APK_CACHE build-base
    sudo apk add --cache-dir $PATH_APK_CACHE libffi-dev
    sudo apk add --cache-dir $PATH_APK_CACHE openssl-dev
    sudo apk add --cache-dir $PATH_APK_CACHE bzip2-dev
    sudo apk add --cache-dir $PATH_APK_CACHE zlib-dev
    sudo apk add --cache-dir $PATH_APK_CACHE xz-dev
    sudo apk add --cache-dir $PATH_APK_CACHE openjdk21-jre
    sudo apk add --cache-dir $PATH_APK_CACHE libc6-compat
    sudo apk cache clean

    echo "Instalando asdf..."
    git clone https://github.com/asdf-vm/asdf.git $PATH_ASDF_CACHE/.asdf --branch v0.14.1
    echo -e '\n. $PATH_ASDF_CACHE/.asdf/asdf.sh' >> ~/.bashrc
    echo -e '\n. $PATH_ASDF_CACHE/.asdf/completions/asdf.bash' >> ~/.bashrc
    echo -e '\nalias cicdcli="java -jar $PATH_CICDCLI_CACHE/cicdcli.jar"' >> ~/.bashrc
    source ~/.bashrc

    echo "Instalando CLI de CICD en cache"
    curl -u $NEXUS_USER:$NEXUS_PASSWORD "$NEXUS_HTTP_PROTOCOL://$NEXUS_DOMAIN:$NEXUS_PORT/repository/$NEXUS_MVN_REPOSITORY/$NEXUS_MVN_CICDCLI_JAR_SUBPATH" -o $PATH_CICDCLI_CACHE/cicdcli.jar

else
    echo "No se borra el contenido de los directorios de caché, trabajando con lo que exista"
fi
