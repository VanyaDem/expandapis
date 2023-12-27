# ExpandAPIs Task Submission

This repository contains my submission for the ExpandAPIs task. 
I've diligently implemented the specified requirements and functionalities outlined in the task description.
The codebase reflects my understanding and application of best practices in software development.

Throughout this project, I aimed to craft a robust and efficient solution, ensuring scalability and adherence
to industry standards. The implemented features underscore my proficiency in leveraging technologies to address
complex challenges effectively.

I welcome feedback and suggestions for further enhancements or improvements. Thank you for reviewing my submission.

# API Documentation

## /user/add (POST)
- **Description:** Registers a new user based on provided data in the request body.
- **Request body:**
  ```json
  {
    "username": "any username",
    "password" : "any password"
  }

- **Response**
  - **Code 201** upon successful user creation.
  - **Code 400** for incorrect or conflicting data.

## /user/authenticate  (POST)

- **Description:** Authenticates the user and returns an access token.
- **Request body:**
  ```json
  {
    "username": "any username",
    "password": "any password"
  }

- **Response**
    ```json
    {
      "token": "some.JWT"
    }

- **Code 201** along with an access token upon successful authentication.
- **Code 400** for incorrect or conflicting data.

## /products/add (POST)
- **Description:** Creates a table with records in the database.
- **Request body:**
  ```json
  {
  "table" : "table name",
  "records" : [
    {
      "columnName" : "value",
      "columnName" : "value"
    },
    {
      "columnName" : "value",
      "columnName" : "value"
    }
  ]
  }

- **Response**
    - **Code 201** upon successful table creation with corresponding columns.
    - **Code 401**  for expired token or unauthorized access.
    - **Code 400**  if the token was corrupted or different columns in the records

## /user/all/{tableName} (GET)
- **Description:** Return records from the specified table in JSON format.

- **Response**

   ```json
  [
    {
      "columnName" : "value",
      "columnName" : "value"
    },
    {
      "columnName" : "value",
      "columnName" : "value"
    }
  ]

- **Code 201** upon successful user creation.
- **Code 401**  for expired token or unauthorized access.
- **Code 400**  if the token was corrupted or different columns in the records

