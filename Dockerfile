# Build Stage
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
# Download dependencies first to cache them
RUN mvn dependency:go-offline
COPY src ./src
# Build the application
RUN mvn clean package -DskipTests

# Run Stage
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
# Copy the built jar from the build stage
COPY --from=build /app/target/projeto-grupo-6-1.0.0.jar app.jar

# Expose the application port
EXPOSE 7000

# Environment variables (can be overridden in docker-compose)
ENV DB_URL=jdbc:mysql://db:3306/sistema_barbearia?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
ENV DB_USER=root
ENV DB_PASSWORD=12345678
ENV PORT=7000

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
