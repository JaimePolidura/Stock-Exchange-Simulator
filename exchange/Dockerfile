FROM amazoncorretto:17-alpine

COPY target/exchange-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java","-jar","/app.jar"]

#docker run -it -p 8081:8081 sxs-exchange AMZN