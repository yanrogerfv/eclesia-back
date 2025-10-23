#FROM eclipse-temurin:17-jdk-alpine AS builder
#WORKDIR /app
#COPY .mvn/ .mvn
#COPY mvnw pom.xml ./
#RUN chmod +x mvnw  # Dá permissão de execução
#COPY src ./src
#RUN ./mvnw clean package -DskipTests
#FROM eclipse-temurin:17-jre-alpine
#WORKDIR /app
#COPY --from=builder /app/target/eclesia-*.jar app.jar
#USER 1000
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "app.jar"]

#=========================================================================

# Stage 1: Build the application using Maven and a full JDK
# We use a specific version for reproducibility.
# You can change '17' to match your project's Java version (e.g., 11 or 21)
FROM eclipse-temurin:17-jdk-jammy as builder

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven wrapper and pom.xml to download dependencies first
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Download dependencies. This step is cached by Docker if pom.xml doesn't change.
RUN ./mvnw dependency:go-offline

# Copy the rest of your source code
COPY src ./src

# Build the application, skipping tests for a faster build
RUN ./mvnw package -DskipTests

# Stage 2: Create the final, small image with only the JRE and the application .jar
# We use a JRE image because it's much smaller than a JDK.
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copy the built .jar file from the 'builder' stage
# Make sure your artifactId in pom.xml matches 'app.jar' or change the name here.
# By default, Spring Boot creates a .jar with a version number. Find it in your 'target' folder.
# Example: target/your-project-name-0.0.1-SNAPSHOT.jar
COPY --from=builder /app/target/*.jar app.jar

# Expose the port that your Spring application runs on (default is 8080)
EXPOSE 8080

# The command to run your application
ENTRYPOINT ["java", "-jar", "app.jar"]