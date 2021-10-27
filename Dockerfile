FROM  openjdk:8-jdk-alpine

#WORKDIR /usr/app

ARG JAR_FILE=./build/libs/successmetrics-*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "${JAVA_OPTS}", "-jar", "app.jar"]

