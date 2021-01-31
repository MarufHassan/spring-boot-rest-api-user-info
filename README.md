# Spring Boot Rest API - User Information
Spring boot REST API project using Spring data JPA which connects to MySQL database.

## APP that stores users data

Project Specification
-------------
- Create an APP that stores user data
- Data is composed of first name, last name, address (street, city, state and zip)
- The app should create the following User types (Parent, Child). The child cannot have an address and must belong to a parent
- App should have API to:
	- Delete user data
	- Create user data
	- Update user data
- Data can be saved in a file or a DB (I used DB)
- Readme file describing how to install/run the application
- The project has to provide a mechanism that tests the application to guarantee that it works properly

Used Tools
-----
- Java 11
- Spring Boot v2.4.2
- JUnit v4.13.1
- Maven v3.6.3
- MySQL Community Server 8.0.23

Database Setup
---------------
### Using MySql Workbench:

- From the Server menu, choose Data Import
- Choose Import from Self-Contained File.
- Select the `users_database` from the `database` directory.
- In the Default Target Schema: Create a new schema name `users_database`
- Select the options: `Dump Structures Only`
- Click Start Import

### Using phpMyAdmin
- Log into cPanelhow to use phpmyadmin to import a database
- Under Databases, open phpMyAdmin by clicking the phpMyAdmin icon
- On the left, select the database that you will be working with
- Click Import in the top menu
- Under File to Import, click Browse and select the backup file `users_database` from the `database` directory.
- Click Go at the bottom right to import the database file

Database Configuration in Spring Boot
-------------------------------------
Change database connection config in `src/main/resources/application.properties`
```
# Database Properties
spring.datasource.url = jdbc:mysql://localhost:3306/users_database
# Change as need
spring.datasource.username = root
# change as need
spring.datasource.password = root
```

Project Setup
-------------
- Clone and open in Visual Studio Code (other IDE is also fine)
- Change database connection config in `src/main/resources/application.properties`
- Install maven dependencies using IDE auto import or using the command ``mvn install``
- Run the app using ``mvn spring-boot:start`` from project root directory.
- Browse http://localhost:8080/api/v1/users/parents or http://localhost:8080/api/v1/users/childs
- Test the app using ``mvn test`` from project root directory.
    
API Doc & Sample
----------------
- List all parent users
    ```
    GET /api/v1/users/parents
    ```
- Get a parent user using specific Id
    ```
    GET /api/v1/users/parents/1
    ```
- List all child users
    ```
    GET /api/v1/users/childs
    ```
- Get a child user using specific Id
    ```
    GET /api/v1/users/childs/1
    ```
- Create new parent
    ```
    POST /api/v1/users/parents
    ```

    Body:
    ```json
    {
        "firstname": "Saiful",
        "lastname": "Islam",
        "address": {
            "street": "Port Colony",
            "city": "Bandar",
            "state": "Chittagong",
            "zip": "7000"
        }
    }
    ```
    Content-Type:
    ```
    application/json
    ```

- Create new child
    ```
    POST /api/v1/users/childs
    ```

    Body:
    ```json
    {
        "firstname": "Saiful",
        "lastname": "Islam",
        "parent": {
            "firstname": "Siddique",
            "lastname": "Hossain",
            "address": {
                "street": "Port Colony",
                "city": "Bandar",
                "state": "Chittagong",
                "zip": "7000"
            }
        }
    }
    ```

- Update parent
    ```
    PUT /api/v1/users/parents/1
    ```
    Body:
    ```json
    {
        "id": 1,
        "firstname": "Mahmud",
        "lastname": "Hassan",
        "address": {
            "street": "Port Colony",
            "city": "Bandar",
            "state": "Chittagong",
            "zip": "7000"
        }
    }
    ```
    
    Content-Type:
    ```
    application/json
    ```
- Update child
    ```
    PUT /api/v1/users/childs/1
    ```
    Body:
    ```json
    {
        "id": 1,
        "firstname": "Afjal",
        "lastname": "Hossain",
        "parent": {
            "firstname": "Siddique",
            "lastname": "Hossain",
            "address": {
                "street": "Port Colony",
                "city": "Bandar",
                "state": "Chittagong",
                "zip": "7000"
            }
        }
    }
    ```
    
    Content-Type:
    ```
    application/json
    ```
- Delete parent user
    ```
    DELETE /api/v1/users/parents/1
    ```
- Delete child user
    ```
    DELETE /api/v1/users/childs/1
    ```

Database Diagram
----------------
![users database](/database/database-schema.png)

Bidirectional Mapping
------------------
Parent - Child (One-To-Many)

Parent - Address (One-To-One)

Child - Parent (Many-To-One)

Test
----

Test the app using ``mvn test`` from project root directory. If everything is fine, then you should see the following types of output in the console.

    [INFO] Results:
    [INFO]
    [INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0
    [INFO]
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time:  12.728 s
    [INFO] ------------------------------------------------------------------------

Writing Tests
-------------
In the spirit of Test-Driven Development (TDD), I wrote the tests first in the UserInfoApplicationTests file: `src\test\java\com\marufhassan\userinfo\UserInfoApplicationTests.java`

```java
@Test
public void testGetAllParents() {}

@Test
public void testGetAllChilds() {}

@Test
public void testGetParentById() {}

@Test
public void testGetChildtById() {}

@Test
public void testCreateParent() {}

@Test
public void testCreateChild() {}

@Test
public void testUpdateParent() {}

@Test
public void testUpdateChild() {}

@Test
public void testDeleteParent() {}

@Test
public void testDeleteChild() {}
```