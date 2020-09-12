FROM openjdk:8

ADD target/sport-services.jar /
EXPOSE 8080
CMD ["java", "-jar", "sport-services.jar"]
