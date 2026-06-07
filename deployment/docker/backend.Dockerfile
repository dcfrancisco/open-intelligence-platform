FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY backend ./backend
RUN mvn -f backend/pom.xml -pl oip-application -am package -DskipTests

FROM eclipse-temurin:21-jre
RUN apt-get update \
    && apt-get install -y --no-install-recommends wget \
    && rm -rf /var/lib/apt/lists/*
WORKDIR /app
COPY --from=build /workspace/backend/oip-application/target/oip-application-0.1.0-SNAPSHOT.jar /app/oip-application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/oip-application.jar"]
