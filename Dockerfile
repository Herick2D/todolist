FROM ubuntu:latest AS build
LABEL authors="Herick"

RUN apt-get update && apt-get install -y openjdk-17-jdk maven

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /app/target/todolist-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
