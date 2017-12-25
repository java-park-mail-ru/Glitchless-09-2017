# Rhythm Blast [![Build Status](https://travis-ci.org/java-park-mail-ru/Glitchless-09-2017.svg?branch=dev)](https://travis-ci.org/java-park-mail-ru/Glitchless-09-2017) [![codecov](https://codecov.io/gh/java-park-mail-ru/Glitchless-09-2017/branch/master/graph/badge.svg)](https://codecov.io/gh/java-park-mail-ru/Glitchless-09-2017)

Арканоид на стероидах <s>и на котлине</s>.

Игра: https://glitchless.ru

Бекенд: https://glitchless.ru/api


## Технологии

- Kotlin, Java
- Spring
- Docker
- Ansible, Docker registry, Travis CI

Гитхаб с фронтендом: https://github.com/frontend-park-mail-ru/2017_2_glitchless


## Инструкции по запуску

```sh
docker build -t backend .

docker run \
    --name db \
    -d \
    -e POSTGRES_USER=backend -e POSTGRES_PASSWORD=qwerty -e POSTGRES_DB=glitchlessdb \
    postgres

docker run \
    --name backend \
    -d \
    -p 8081:80 \
    -e DATABASE_USER=backend -e DATABASE_PASSWORD=qwerty \
    -e DATABASE_URL=jdbc:postgresql://db/glitchlessdb \
    --link db \
    backend 

# будет доступен на http://localhost:8081
```

## Инструкция по запуску тестов

```sh
docker-compose -f docker-compose-test.yml up --abort-on-container-exit
```

## Команда Glitchless

- [Михаил Волынов](https://github.com/StealthTech)
- [Никита Куликов](https://github.com/LionZXY)
- [Олег Морозенков](https://github.com/reo7sp)
- [Юрий Голубев](https://github.com/Ansile)
