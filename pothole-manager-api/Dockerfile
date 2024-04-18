FROM eclipse-temurin:17-jdk-focal
WORKDIR /pothole-backend
COPY /build/libs/*.jar pothole-manager-api.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=manager-dev", "-jar", "pothole-manager-api.jar"]