# Use an Eclipse Temurin Maven image to build the application
FROM maven:3.9.0-eclipse-temurin-17 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code
COPY src /app/src

# Package the application, skipping tests
RUN mvn package -DskipTests -DskipITs

# Use an Eclipse Temurin runtime image for the final image
FROM eclipse-temurin:17-jre-jammy

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/bookingApp-0.0.1-SNAPSHOT.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]