FROM openjdk:1.8
VOLUME /tmp
EXPOSE 8761
ADD ./target/server-eureka-0.0.1-SNAPSHOT.jar.jar server-eureka.jar
ENTRYPOINT ["java", "-jar", "server-eureka.jar"]
