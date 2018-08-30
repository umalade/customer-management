Feature: User can successfully get, create, delete, and update customers

  Scenario: User gets a created customer
    When the user creates a customer
     And the customer is successfully created
     And the user gets the created customer
    Then the user receives status code of 200
     And the retrieved customer is correct
  
  Scenario: User gets an existing customer
   Given an customer exists
    When the user gets the created customer
    Then the user receives status code of 200
     And the retrieved customer is correct
  
  Scenario: User deletes a created customer
   Given an customer exists
     And the user deletes the created customer
     And the user receives status code of 204
    When the user gets the created customer
    Then the user receives status code of 404 
    