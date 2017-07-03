FROM openjdk:latest

MAINTAINER cj.zhao <cj.zhao@caixintech.com>

ENV DEBIAN_FRONTEND noninteractive

ADD *.jar app.jar

RUN sh -c 'touch /app.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
