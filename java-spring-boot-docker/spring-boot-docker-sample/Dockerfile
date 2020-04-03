FROM openjdk:8-jdk-alpine

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS

COPY target/*.jar app.jar
RUN sh -c 'touch /app.jar'

EXPOSE 30000

ENTRYPOINT java ${JAVA_OPTS:- -Xms384m -Xmx384m -XX:MaxMetaspaceSize=128m} \ 
           -Djava.security.egd=file:/dev/./urandom \
           -jar /app.jar