FROM adoptopenjdk/openjdk11
EXPOSE 8761
ARG JAR_FILE=build/libs/eureka-server-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} eureka-server.jar
ENTRYPOINT ["java", "-jar","/eureka-server.jar"]