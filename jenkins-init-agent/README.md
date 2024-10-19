# Local compilation
```
docker build -t generic-init-agent:0.0.0 -f generic-init-agent.Dockerfile .
```

# DockerHub
```
# Login
docker login -u srmmll # Use token

# Push
docker tag generic-init-agent:0.0.0 srmmll/generic-init-agent:0.0.0
docker push srmmll/generic-init-agent:0.0.0

# Pull
docker pull srmmll/generic-init-agent:0.0.0
```

# Nexus
```
```
