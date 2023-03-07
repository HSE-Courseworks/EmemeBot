FROM bellsoft/liberica-openjdk-alpine:17

RUN mkdir /app && mkdir /app/src && mkdir /app/src/main && mkdir /app/src/main/resources
COPY ./target/*.jar /app/application.jar
COPY /src/main/resources/ /app/src/main/resources

ENTRYPOINT ["java","-jar","/app/application.jar"]