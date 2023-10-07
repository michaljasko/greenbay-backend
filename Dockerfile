FROM openjdk:17-alpine
ENV TZ=UTC
VOLUME /tmp
COPY target/greenbay-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 8080