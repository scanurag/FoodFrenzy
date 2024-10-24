FROM openjdk:17

WORKDIR /app

COPY target/FoodFrenzy-0.0.1-SNAPSHOT.jar /app/FoodFrenzy-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "FoodFrenzy-0.0.1-SNAPSHOT.jar"]

