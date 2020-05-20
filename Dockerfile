FROM openjdk:8-jdk-alpine

WORKDIR /usr/src/app

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} ./success-metrics.jar

ENTRYPOINT ["java","-jar","./success-metrics.jar"]

