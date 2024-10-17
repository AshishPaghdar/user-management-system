   # User-Management-System
This is an user management system developed using Spring Boot framework. This project is managing user such as creating user and deleting event based on their role. These technologies have been implemented in this project.

    1. Spring-security(JWT),
    2. Flyway for the database migration(for database script execution)
    3. Global Exception, 

# There are the following steps for the setup of the project.
    1. JDK 17 
    2. Spring-boot 3.2.4
    3. Mysql server(For database)
    4. Redis implementation for Caching
    
# Create the database in Mysql server.
  create database user_application
  Set the database configuration in the properties file. Like Username, Password, URL, etc..

 # Start the event management system.
 ```sh
mvn spring-boot:run
```

# Flyway for the database migration 
   This "V1__create_user_table.sql" database script is automatically executed when you start the application.

# Install Redis server in your system.
```sh 
  sudo apt update
  sudo apt install redis-server
  sudo service redis-server start
```
Note: Default redis configuration. Put the below configurations in application.properties file.
redis.host=localhost
redis.port=6379

# Login(Token Generation) URL for Admin.

    curl --location 'http://localhost:8081/api/v1/auth/login' \
    --header 'Content-Type: application/json' \
    --header 'Cookie: COOKIE_SUPPORT=true; GUEST_LANGUAGE_ID=en_US' \
    --data-raw '{
    "id" : "1",
    "email": "admin1@example.com",
    "password": "password123",
    "role":"ROLE_ADMIN"
    }'
```

> Note: `We have put(User-Managemnt-app.postman_collection.json) the collection of APIs. You can import the APIs collection into postman`
