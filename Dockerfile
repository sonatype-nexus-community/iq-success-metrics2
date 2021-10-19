FROM  openjdk:8-jdk-alpine

WORKDIR /usr/app

ARG JAR_FILE=./build/libs/successmetrics-*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "-Ddata.dir=/tmp/successmetrics", "app.jar"]
