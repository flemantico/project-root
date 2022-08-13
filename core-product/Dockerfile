FROM openjdk:1.8
VOLUME /tmp
# EXPOSE 8761  #No va porque el puerto es aleatorio en el application.properties
ADD ./target/core-product-0.0.1-SNAPSHOT.jar core-product.jar
ENTRYPOINT ["java", "-jar", "core-product.jar"]
