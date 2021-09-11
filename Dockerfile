FROM  openjdk:8-jdk-alpine

ARG JAR_FILE=build/lib/successmetrics-*.jar

WORKDIR /var/tmp

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

