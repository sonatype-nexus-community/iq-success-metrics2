FROM  openjdk:8-jdk-alpine

ARG JAR_FILE=build/libs/successmetrics-*.jar

COPY ${JAR_FILE} /var/tmp/app.jar

ENTRYPOINT ["java", "-jar", "/var/tmp/app.jar"]

