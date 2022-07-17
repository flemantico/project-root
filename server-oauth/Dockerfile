FROM openjdk:1.8
VOLUME /tmp
EXPOSE 9100
ADD ./target/server-oauth-0.0.1-SNAPSHOT.jar server-oauth.jar
ENTRYPOINT ["java", "-jar", "server-oauth.jar"]
