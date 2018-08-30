# Customer Management REST API 

Demonstrates a simple RESTful web service using Spring MVC and Java. This web service provides an in-memory customer management service, with the capability to get a single customer, get all customers, create a customer, delete a customer, and update a customer.


## Starting the Customer Management System
To start this web service, install [Maven](https://maven.apache.org/install.html) and execute the following command

    mvn spring-boot:run
    
Once the web service is started, it can be reached at

    http://localhost:8080/customer
    
    GET - http://localhost:8080/customer  - list of all existing customers
    GET - http://localhost:8080/customer/{id} - returns the customer corresponding to the supplied id
    POST - http://localhost:8080/customer - creates a new customer based on the payload
    PUT - http://localhost:8080/customer/{id} - updates the existing customer
    DELETE - http://localhost:8080/customer/{id} - deletes the customer with the id matching to the supplied id.
