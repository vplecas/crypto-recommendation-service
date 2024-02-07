FROM eclipse-temurin:17-jdk-jammy

EXPOSE 8080

ADD target/crypto-recommendation-service-0.0.1-SNAPSHOT.jar crypto-recommendation-service-0.0.1-SNAPSHOT.jar

ENTRYPOINT [ "java", "-jar", "/crypto-recommendation-service-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=deploy" ]