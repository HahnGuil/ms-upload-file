FROM eclipse-temurin:21-jdk

WORKDIR /app

# Instala o Maven 3.8
RUN apt-get update && \
    apt-get install -y wget && \
    wget https://archive.apache.org/dist/maven/maven-3/3.8.8/binaries/apache-maven-3.8.8-bin.tar.gz && \
    tar -xzf apache-maven-3.8.8-bin.tar.gz -C /opt && \
    ln -s /opt/apache-maven-3.8.8/bin/mvn /usr/bin/mvn && \
    rm apache-maven-3.8.8-bin.tar.gz

ENV MAVEN_HOME=/opt/apache-maven-3.8.8
ENV PATH=$PATH:$MAVEN_HOME/bin

COPY ./target/*.jar app.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "app.jar"]