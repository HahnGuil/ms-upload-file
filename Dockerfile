FROM eclipse-temurin:21-jdk

WORKDIR /app

# More specific COPY to find the JAR file
COPY ./target/*.jar app.jar

EXPOSE 8085

ENTRYPOINT ["java","-jar","app.jar"]