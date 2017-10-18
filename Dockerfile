FROM maven:3.5-jdk-8

WORKDIR /tmp/glitchless
COPY . .

CMD mvn install && mvn cobertura:cobertura && curl -s https://codecov.io/bash | bash