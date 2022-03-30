FROM maven:3.6.0-jdk-11-slim AS build
COPY src /src
COPY pom.xml pom.xml
RUN mvn --quiet clean install -DskipTests

#
# Package stage
#
FROM openjdk:11-jre-slim
COPY --from=build /target/challenge-0.0.1-SNAPSHOT.jar /usr/local/lib/demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/demo.jar"]