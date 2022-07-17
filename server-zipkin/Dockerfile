FROM openjdk:1.8
VOLUME /tmp
EXPOSE 9411
ADD ./server-zipkin-2.23.4-exec.jar server-zipkin.jar
ENTRYPOINT ["java", "-jar", "server-zipkin.jar"]
