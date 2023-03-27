FROM adoptopenjdk/openjdk17:alpine-jre
WORKDIR /app
COPY target/gateway-0.0.1.jar .
EXPOSE 8080
CMD ["java", "-jar", "gateway-0.0.1.jar"]
