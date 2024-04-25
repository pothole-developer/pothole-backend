FROM eclipse-temurin:17-jdk-focal
WORKDIR /pothole-backend/pothole-manager-api
COPY /build/libs/*.jar pothole-manager-api.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=manager-dev", "-jar", "pothole-manager-api.jar"]