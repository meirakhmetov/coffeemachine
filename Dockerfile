# Используем официальный образ OpenJDK 23
FROM openjdk:23-jdk-slim
LABEL authors="Meir.Akhmetov"

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем собранный JAR-файл в контейнер
COPY target/coffeemachine-0.0.1-SNAPSHOT.jar app.jar

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]