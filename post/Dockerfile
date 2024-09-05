FROM openjdk:17-jdk-oracle
LABEL authors="Jehad.Hamayel"

COPY target/*.jar postservice.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","postservice.jar"]

