FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY . .

RUN ./gradlew build -x test

RUN mv build/libs/$(ls build/libs/ | grep -v plain) app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]