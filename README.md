# Backend
This is a calorie counting application - backend, frontend is at this address [here](https://github.com/Szczgl/calorie_counting_application_vaadin)


## Prerequisites

 Java 17 or later
 H2 database with writing to a file


## Other technologies used:

- Spring Boot
- Hibernate
- Vaadin
- Lombok
- JUnit


## Run

 To start the application

 ./gradlew bootRun for backend

 ./gradlew bootRun for frontend

 Access the application at http://localhost:8081


## External API

The application is used to count calories based on initial data set by the user, such as daily calorie requirement.
We have two integrated API functionalities:
1. API "exercisedb" for downloading instructions for performing exercises
2. API "edamam" used to retrieve the weight and caloric content of ingredients


## Other

The program includes e-mail notifications sent at the end of the day about calorie deficit or deficiency


## Contact

If you have any questions or comments, please write to szczgl@wp.pl
