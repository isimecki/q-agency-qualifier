# q-agency-qualifier

## Setup instructions:

- have git installed
- have JDK 17+ installed and JAVA_HOME set to jdk17+
- have Maven installed
- have MySQL Server 8.0, MySQL Workbench installed - download installer: https://dev.mysql.com/downloads/installer/ and follow instructions

## Run instructions:

- start MySQL server - instructions: https://dev.mysql.com/doc/mysql-startstop-excerpt/8.0/en/
- create a user "root" with password "adminadmin"
- start MySQL Workbench and connect to database (by default running on localhost:3306)
- with a shell of choice, clone repo using "git clone" and navigate to project root directory
- in MySQL Workbench execute db_init.sql script, found in project root dir
- execute: "./mvnw spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=dev" without double quotes to run the app
- access app via browser with url: localhost:8080

## Troubleshooting

- ensure that spring.datasource.url property in application-dev.properties has corresponding address to the running MySQL server
- possible issues with running MySQL Server on Windows - run mysqld with command line arguments: --defaults-file="C:\ProgramData\MySQL\MySQL Server 8.0\my.ini"
- if MySQL server setup is too tedious execute: "./mvnw spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=test" without double quotes to run the app with integrated h2 database