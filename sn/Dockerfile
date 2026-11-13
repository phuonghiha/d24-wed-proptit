
# Giai đoạn 1: Build ứng dụng
FROM gradle:9.7.1-jdk25 AS build

WORKDIR /app

COPY . .

RUN chmod +x gradlew
RUN ./gradlew bootJar -x test

# Giai đoạn 2: Chạy ứng dụng
FROM eclipse-temurin:25-jre

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
