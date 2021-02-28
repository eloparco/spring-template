FROM gradle:6.8-jdk11 AS build
WORKDIR /app
COPY . .
RUN gradle build --no-daemon

FROM openjdk:11-jre-slim
COPY --from=build /app/build/libs/*.jar spring-boot-app.jar
ENTRYPOINT ["java", "-jar", "spring-boot-app.jar"]