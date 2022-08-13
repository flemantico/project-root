FROM openjdk:1.8
VOLUME /tmp
EXPOSE 8002
ADD ./target/bus-item-0.0.1-SNAPSHOT.jar service-item.jar
ENTRYPOINT ["java", "-jar", "bus-item.jar"]
