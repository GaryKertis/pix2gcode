### Pix2Gcode

How to deploy.

```
clone pix2gcode-client into this folder.
clone minigrep into this folder.
docker build -t rest-service .
docker run --tmpfs /tmp -p 8080:8080 rest-service
```
