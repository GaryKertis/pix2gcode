FROM rust:1.55
WORKDIR /usr/src/minigrep
COPY ./minigrep .
RUN cargo install --path .

FROM openjdk:8 as build
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src ./src
RUN ./mvnw package

FROM openjdk:8
COPY --from=0 /usr/local/cargo/bin/minigrep /usr/local/bin/minigrep
WORKDIR /usr/src/rest-service
ARG JAR_FILE=target/*.jar
COPY --from=build /app/target/*.jar app.jar
RUN addgroup --system gk && adduser --system gk --ingroup gk
RUN chown -R gk:gk .
USER gk:gk
ENTRYPOINT ["java","-jar","app.jar"]
