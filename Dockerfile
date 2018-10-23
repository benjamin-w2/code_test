FROM openjdk:11.0.1-jre-slim

COPY target/code_test-1.0-SNAPSHOT.jar app.jar

CMD java -jar app.jar