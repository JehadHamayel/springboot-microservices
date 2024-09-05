FROM openjdk:17-jdk-oracle
LABEL authors="Jehad.Hamayel"

COPY target/*.jar userservice.jar

EXPOSE 8090

ENTRYPOINT ["java","-jar","userservice.jar"]

