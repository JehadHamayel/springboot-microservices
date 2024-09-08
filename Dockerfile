FROM openjdk:17-jdk-oracle
LABEL authors="Jehad.Hamayel"

COPY user/target/*.jar userservice.jar
COPY post/target/*.jar postservice.jar

EXPOSE 8080 8090

ENTRYPOINT ["java","-jar","userservice.jar", "postservice.jar"]



