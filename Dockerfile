#FROM ubuntu:latest
#LABEL authors="yrfv"
#
#ENTRYPOINT ["top", "-b"]

#FROM eclipse-temurin:17-jdk-alpine
#VOLUME /tmp
#COPY target/*.jar app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]

FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw  # Dá permissão de execução
COPY src ./src
RUN ./mvnw clean package -DskipTests
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/eclesia-*.jar app.jar
USER 1000
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]