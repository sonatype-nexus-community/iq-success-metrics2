FROM openjdk:8-jdk-alpine

WORKDIR /usr/src/app

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} ./app.jar

ENTRYPOINT ["java","-jar","./app.jar"]

