## job4j_cinema
[![Java CI with Maven](https://github.com/Olegsander48/job4j_cinema/actions/workflows/maven.yml/badge.svg)](https://github.com/Olegsander48/job4j_cinema/actions/workflows/maven.yml)
## Описание проекта:
job4j_cinema является проектом-киноафишей. Он предоставляет функциональность взаимодействия клиента с онлайн-сервисом покупки билетов в кинотеатр. Имеется возможность просмотра доступных фильмов и приобретения билетов на конкретные сеансы. 

## Стек технологий:
- Java 17
- PostgreSQL 17
- Maven 3.9.9
- Liquibase 4.15.0
- Spring boot-web 3.5.3
- Spring boot-thymeleaf 3.5.3
- Spring boot-test 3.5.3
- Sql2o 1.6.0
- H2 db 2.2.220
- Lombok 1.18.38
- Jacoco 0.8.10

## Требования к окружению:
- Java 17
- PostgreSQL 17
- Maven 3.9.9

## Запуск проекта:
1. Необходимо создать базу данных **cinema**
```SQL
create database cinema
```
2.  Теперь небходимо создать таблицы про помощи скриптов Liquibase. Необходимо выполнить фазу **через Maven**:
```
liquibase:update
```
Теперь у нас БД готова к использованию

3. Перейдите на [страницу](https://github.com/Olegsander48/job4j_cinema/actions/workflows/jar.yml) и выберите последний выполненный worflow, скачайте my-app-jar
4. Из архива достаньте файл job4j_cinema-0.0.1-SNAPSHOT.jar
5. Перейдите в директорию с jar-файлом, выполните команду:
```CMD
java -jar job4j_cinema-0.0.1-SNAPSHOT.jar
```
6. Приложение запущено, перейдите по [адресу, указанному в командной строке,](http://localhost:8080/) и пользуйтесь приложением
   
## Интерфейс приложения:
1. Страница регистрации пользователя
![Страница регистрации пользователя](https://github.com/Olegsander48/job4j_cinema/blob/main/images/registration.png)
2. Страница аунтификации пользователя
![Страница аунтификации пользователя](https://github.com/Olegsander48/job4j_cinema/blob/main/images/login.png)
3. Страница фильмов, показываемых в кинотеатрах
![Страница фильмов, показываемых в кинотеатрах](https://github.com/Olegsander48/job4j_cinema/blob/main/images/films.png)
4. Страница сессий в кинотеатрах
![Страница сессий в кинотеатрах](https://github.com/Olegsander48/job4j_cinema/blob/main/images/schedule.png)
5. Страница покупки билета
![Страница покупки билета](https://github.com/Olegsander48/job4j_cinema/blob/main/images/buy_ticket.png)

## Мои контакты:
Обращайтесь по любым вопросам и обратной связи, буду рад ответить :blush::blush:
<p align="center">
<a href="https://t.me/Olegsander48" target="blank"><img align="center" src="https://cdn.jsdelivr.net/npm/simple-icons@3.0.1/icons/telegram.svg" alt="olegsander48" height="30" width="30" /></a>&nbsp;
<a href="https://linkedin.com/in/aleksandr-prigodich-b7028a1b3" target="blank"><img align="center" src="https://cdn.jsdelivr.net/npm/simple-icons@3.0.1/icons/linkedin.svg" alt="aleksandr-prigodich" height="30" width="30" /></a>&nbsp;
<a href="http://discord.com/users/olegsander48" target="blank"><img align="center" src="https://cdn.jsdelivr.net/npm/simple-icons@3.0.1/icons/discord.svg" alt="olegsander48" height="40" width="30" /></a>&nbsp;
<a href="mailto:prigodichaleks@gmail.com?subject=Hi%20Aleks.%20I%20saw%20your%20GitHub%20profile%20&body=I'm%20writing%20to%20you%20because%20...%0A"><img align="center" src="https://cdn.jsdelivr.net/npm/simple-icons@3.0.1/icons/gmail.svg" alt="prigodichaleks@gmail.com" height="40" width="30" /></a>&nbsp;
</p>
