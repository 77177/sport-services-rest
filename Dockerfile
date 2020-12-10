FROM openjdk:8

COPY target/sport-services-rest.jar /

CMD ["java","-Dspring.profiles.active=docker", "-jar", "sport-services-rest.jar"]
