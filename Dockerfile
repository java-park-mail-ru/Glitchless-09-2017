FROM maven:3.5-jdk-8

WORKDIR /glitchless
RUN curl -s https://codecov.io/bash -o codecov && chmod +x codecov
COPY . .

CMD mvn install
CMD mvn cobertura:cobertura && ./codecov
CMD mvn package && cp ./target/*.jar /artifacts