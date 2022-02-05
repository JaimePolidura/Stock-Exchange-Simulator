FROM amazoncorretto:17-alpine

COPY target/gateway-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-Xms6144M", "-Xmx6144M", "-jar","/app.jar"]
