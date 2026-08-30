# 📚 CONCEPT: Multi-stage Docker Builds
# Stage 1: Build the application using a heavy image that includes Maven and the JDK.
# Stage 2: Run the application using a tiny, lightweight image that only has the JRE.
# This prevents our final image from containing source code and build tools, keeping it small and secure.

# ==========================================
# STAGE 1: Build
# ==========================================
# Using the official Maven image because the local maven wrapper (mvnw) is missing
FROM maven:3.9.8-eclipse-temurin-21-alpine AS builder

WORKDIR /app

# Step 1: Copy only the pom.xml and download dependencies.
# We do this first because Docker caches layers. If pom.xml hasn't changed,
# Docker skips downloading dependencies on the next build, saving minutes of time.
COPY pom.xml .

# Download dependencies (go offline so they are cached)
RUN mvn dependency:go-offline -B

# Step 2: Copy the actual source code and build the .jar
COPY src ./src
# We skip tests during the Docker build because they require a live database
RUN mvn package -DskipTests

# ==========================================
# STAGE 2: Runtime
# ==========================================
# We use the JRE (Java Runtime Environment) instead of JDK.
# It is much smaller because it doesn't include the compiler.
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Create a non-root user for security (Running as root in Docker is dangerous)
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy the .jar file from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose port 8080 to the outside world
EXPOSE 8080

# 📚 CONCEPT: Extreme Memory Tuning for 512MB Free Tiers
# -Xmx256m: Hard limit the Heap to 256MB.
# -XX:MaxMetaspaceSize=128m: Limit class metadata to 128MB.
# -Xss256k: Shrink the thread stack size to use less memory per concurrent user.
# -XX:+UseSerialGC: Use the Serial Garbage Collector. It is slightly slower than G1GC/ZGC but uses drastically less memory overhead.
ENTRYPOINT ["java", "-Xmx256m", "-XX:MaxMetaspaceSize=128m", "-Xss256k", "-XX:+UseSerialGC", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
