FROM openjdk:21-jdk
LABEL authors="leniv"
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} tg-bot-vacancies.jar
EXPOSE 3030

ENTRYPOINT ["java", "-jar", "/tg-bot-vacancies.jar"]