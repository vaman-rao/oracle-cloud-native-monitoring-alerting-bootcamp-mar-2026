# ---- Build Stage ----
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
# Download dependencies first (cached layer)
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# ---- Run Stage ----
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Create a non-root user for security
RUN addgroup -S clinic && adduser -S clinic -G clinic
COPY --from=build /app/target/*.jar app.jar
RUN chown clinic:clinic app.jar
USER clinic
EXPOSE 9002
ENTRYPOINT ["java", "-jar", "app.jar"]
