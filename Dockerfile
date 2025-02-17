#FROM    openjdk:17-oracle
#VOLUME  /tmp
#COPY    target/backend-service-mongp*.jar   app.jar
#EXPOSE  8001
#ENTRYPOINT  ["java", "-jar", "app.jar"]
FROM openjdk:21-oracle
VOLUME /tmp
COPY target/*.jar  app.jar
EXPOSE 8001
ENTRYPOINT ["java","-jar", "app.jar"]