FROM openjdk:8u131-jdk

ADD target/order-service.jar /app/dist/order-service.jar

EXPOSE 9008

VOLUME /tmp

ENTRYPOINT java -Dspring.profiles.active=$BOOTIFUL_MICRO_PIZZA_ENV -Djava.security.egd=file:/dev/./urandom -jar /app/dist/order-service.jar
