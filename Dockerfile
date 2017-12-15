FROM maven:3.5-jdk-8

WORKDIR /app
COPY . .

RUN mvn install -B -q -DskipTests

CMD ["java", "-jar", "target/glitchless-backend-1.0-SNAPSHOT.jar"]