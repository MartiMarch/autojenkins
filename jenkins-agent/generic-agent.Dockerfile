FROM alpine:3.20.3
USER root

# Cache
RUN mkdir -p '/opt/cache'
RUN mkdir -p '/opt/cache/apk'
RUN mkdir -p '/opt/cache/asdf'
RUN mkdir -p '/opt/cache/asdf/plugins'
ENV ASDF_DIR='/opt/cache/asdf/.asdf'
ENV ASDF_DATA_DIR='/opt/cache/asdf/plugins'
ENV APK_CACHE_DIR='/opt/cache/apk'

RUN apk add --no-cache bash

CMD ["bash"]