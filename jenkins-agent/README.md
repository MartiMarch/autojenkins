# Local compilation
```
docker build --platform=linux/amd64 -t srmll/generic-agent:0.0.0 -f generic-agent.Dockerfile .
```

# DockerHub
```
# Login
docker login -u srmmll # Use token

# Push
docker tag generic-agent:0.0.0 srmmll/generic-agent:0.0.0
docker push srmmll/generic-agent:0.0.0
 bash
# Pull
docker pull srmmll/generic-agent:0.0.0
```

# Nexus
```
TODO
```

# Cache
Se crean los directorios con el initContainer, también se pueden purgar la cache usando la variable  $CLEAR_CACHE_PATHS.

La librería de Jenkins se ecnarga de cargar todos los *.apk almacenado en el directorio /etc/opt/cache.


``` bash
apk add --allow-untrusted --no-cache ${apk file path}
```

También evitará cargar lo especificado en el yml de configuración utilizando la lista de lo escaneado.
``` bash
apk info -q
```

```
Tdo este contendio he de agregarlo a la libraria, concretamente la parte de asdf

## Esto se traslada a la cache con el script de inicialicación del CI
#RUN apk add --cache-dir $APK_CACHE_DIR curl
#RUN apk add --cache-dir $APK_CACHE_DIR git
#RUN apk add --cache-dir $APK_CACHE_DIR build-base
#RUN apk add --cache-dir $APK_CACHE_DIR libffi-dev
#RUN apk add --cache-dir $APK_CACHE_DIR openssl-dev
#RUN apk add --cache-dir $APK_CACHE_DIR bzip2-dev
#RUN apk add --cache-dir $APK_CACHE_DIR zlib-dev
#RUN apk add --cache-dir $APK_CACHE_DIR xz-dev
#RUN apk cache clean
######

## Esto se traslada a la cache con el script de inicialización del CI
# ASDF installation
#RUN git clone https://github.com/asdf-vm/asdf.git $ASDF_DIR --branch v0.14.1
######

# Shell configuration
#RUN echo -e '\n. $ASDF_DIR/asdf.sh' >> ~/.bashrc
#RUN echo -e '\n. $ASDF_DIR/completions/asdf.bash' >> ~/.bashrc

# Para reconciliar lo cahceado con el pod
# apk update --cache-dir $APK_CACHE_DIR
```

#h2 IMPORTANTE

Para alcanzar el localhost del anftrión desde un contenedro es necesario usar el flag '--network host'

```bash
docker run --network host -it srmmll/generic-agent:0.0.0 bash
```

#h2 CARGAR/LIMPIAR CACHE

```bash
export CLEAR_CACHE_PATHS='yes'
sudo -E bash /init-container-script.sh
export NEXUS_PORT=3333
export NEXUS_DOMAIN=localhost
```

#h2 CARGAR/LIMPIAR CACHE

Revisar binarios requeridos por los plugins de asdf:
```bash
ldd /opt/cache/asdf/.asdf/installs/java/openjdk-21.0.2/bin/java
```

