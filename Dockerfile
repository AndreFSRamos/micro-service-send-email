FROM openjdk:17

RUN mkdir -p usr/src/app
WORKDIR /usr/src/app
ADD . /usr/src/app

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY target/email-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]