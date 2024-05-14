FROM openjdk:17-alpine
ARG PROJECT_FILE_NAME=redis_study-0.0.1-SNAPSHOT.jar
ARG JAR_FILE=build/libs/${PROJECT_FILE_NAME}
COPY build/libs/${PROJECT_FILE_NAME} redis_study.jar
EXPOSE 8081
ENTRYPOINT exec java -jar redis_study.jar