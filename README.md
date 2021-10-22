### Pix2Gcode

How to deploy.

```
clone minigrep
docker build -t rest-service .
docker run --tmpfs /tmp -p 8080:8080 rest-service
```
