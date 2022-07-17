FROM openjdk:1.8
VOLUME /tmp
EXPOSE 8888
ADD ./target/server-configuration-0.0.1-SNAPSHOT.jar.jar server-configuration.jar
ENTRYPOINT ["java", "-jar", "server-configuration.jar"]
