FROM ubuntu:latest as BUILD

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

RUN apt-get install maven -y
RUN mvn clean install

FROM openjdk:17-jdk-slim

EXPOSE 8090

COPY --from=build /target/mestre-sala-be-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]