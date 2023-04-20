FROM openjdk:17

RUN mkdir /app && mkdir /app/src && mkdir -p src/main/resources/attachments && mkdir -p src/main/resources/templates
COPY ./target/*.jar /app/application.jar

ENTRYPOINT ["java","-jar","/app/application.jar"]
