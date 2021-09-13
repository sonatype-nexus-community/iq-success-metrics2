FROM  openjdk:8-jdk-alpine

ARG JAR_FILE=./build/libs/successmetrics-*.jar

ENV APP_HOME=/usr/app

RUN mkdir ${APP_HOME}

WORKDIR ${APP_HOME}

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "-Ddata.dir=/usr/app", "/app.jar"]
