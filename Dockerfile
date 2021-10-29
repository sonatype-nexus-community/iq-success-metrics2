FROM  openjdk:8-jdk-alpine

ARG JAR_FILE=successmetrics-*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "${JAVA_OPTS}", "-jar", "app.jar"]

