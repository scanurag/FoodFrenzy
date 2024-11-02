FROM maven:3.8.3-openjdk-17 as builder

MAINTAINER Amol Koli<koli.amol54@gmail.com>

WORKDIR /app

COPY . .

RUN mvn clean install -DskipTests

FROM gcr.io/distroless/java17-debian11 AS deployer

COPY --from=builder /app/target/*.jar /app/FoodFrenzy.jar

EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "/app/FoodFrenzy.jar"]