FROM ubuntu:latest AS build

RUN apt-get update && apt-get install openjdk-21-jdk maven -y

COPY . .

RUN mvn clean install

FROM openjdk:21-jdk-slim
EXPOSE 8084

COPY --from=build /target/manager-0.0.1-SNAPSHOT.jar /app/manager-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/app/manager-0.0.1-SNAPSHOT.jar"]