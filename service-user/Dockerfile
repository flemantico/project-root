FROM openjdk:1.8
VOLUME /tmp
# EXPOSE 8761  #No va porque el puerto es aleatorio en el application.properties
ADD ./target/service-user-0.0.1-SNAPSHOT.jar service-user.jar
ENTRYPOINT ["java", "-jar", "service-user.jar"]
