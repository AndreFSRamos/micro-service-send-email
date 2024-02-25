FROM openjdk:11

RUN mkdir -p usr/src/app
WORKDIR /usr/src/app
ADD . /usr/src/app

# Certifique-se de que o Maven esteja instalado e execute o mvn package
RUN apt-get update && apt-get install -y maven
RUN mvn package -DskipTests

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY target/email-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]