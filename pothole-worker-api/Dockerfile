FROM eclipse-temurin:17-jdk-focal
WORKDIR /pothole-backend/pothole-worker-api
COPY /build/libs/*.jar pothole-worker-api.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=worker-dev", "-jar", "pothole-worker-api.jar"]