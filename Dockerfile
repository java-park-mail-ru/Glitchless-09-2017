FROM maven:3.5-jdk-8-onbuild

CMD ["java", "-jar", "target/glitchless-backend-1.0-SNAPSHOT.jar"]