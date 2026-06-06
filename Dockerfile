# Этап 1: Сборка проекта (используем образ с установленным Leiningen)
FROM clojure:lein AS builder
WORKDIR /app

# Копируем файлы проекта
COPY project.clj .
COPY src ./src

# Скачиваем зависимости и собираем uberjar
RUN lein deps
RUN lein uberjar

# Этап 2: Запуск (используем легкий образ Java для экономии памяти)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Копируем только готовый JAR-файл из предыдущего этапа
COPY --from=builder /app/target/uberjar/*-standalone.jar /app/app.jar

# Render автоматически прокидывает трафик, укажем порт
EXPOSE 8080

# Команда запуска сервера (передаем аргумент 8080, как мы делали в консоли)
CMD ["java", "-jar", "app.jar", "8080"]