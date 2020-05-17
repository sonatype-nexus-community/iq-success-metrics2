FROM openjdk:8-jdk-alpine

WORKDIR /usr/src/app

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} ./successmetrics2.jar

ENTRYPOINT ["java","-jar","./successmetrics2.jar"]

