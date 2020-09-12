FROM openjdk:8

ADD sport-services.jar /opt/sport-services/
EXPOSE 8080
WORKDIR /opt/sport-services/
CMD ["java", "-jar", "sport-services.jar"]
