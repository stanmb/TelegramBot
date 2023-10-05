# Используем базовый образ Java
FROM openjdk:11
# Установка рабочей директории
WORKDIR /app
# Копирование JAR-файла с вашим приложением в контейнер
COPY target/Bot-1.0-jar-with-dependencies.jar .
# Запуск приложения при запуске контейнера
CMD ["java", "-jar", "Bot-1.0-jar-with-dependencies.jar"]