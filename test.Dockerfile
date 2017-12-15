FROM maven:3.5-jdk-8

WORKDIR /app
RUN curl -s https://codecov.io/bash -o codecov && chmod +x codecov
COPY . .

CMD mvn cobertura:cobertura && ./codecov