FROM rust:1.55 as minigrep
WORKDIR /usr/src/minigrep
COPY ./minigrep .
RUN cargo install --path .

FROM node:13.12.0-alpine as ui

# set working directory
WORKDIR /ui-client
COPY ./pix2gcode-client/package.json ./
COPY ./pix2gcode-client/package-lock.json ./
COPY ./pix2gcode-client ./
RUN npm i
RUN npm run build

FROM openjdk:8 as build
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src ./src
COPY --from=ui /ui-client/build/ src/main/resources/static/
#COPY ./pix2gcode-client/build/ src/main/resources/static/
RUN ./mvnw package

FROM openjdk:8
COPY --from=minigrep /usr/local/cargo/bin/minigrep /usr/local/bin/minigrep
WORKDIR /usr/src/rest-service
COPY --from=build /app/target/*.jar app.jar
RUN addgroup --system gk && adduser --system gk --ingroup gk
RUN chown -R gk:gk .
USER gk:gk
ENTRYPOINT ["java","-jar","app.jar"]
