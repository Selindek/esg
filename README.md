# ESG Test Task - Server

Build the project with Maven then run it form inside an IDE or from the command line:
java -jar target/server-0.0.1-SNAPSHOT.jar

The server listens on port 8080.

Data are stored in an temporary in-memory DB.


## Endpoints

POST /customers  body: JSON representation of a customer entity

stores a new customer in the DB. 
Only the customerRef property is mandatory.

GET /customers 

returns all of the customers as a JSON array.
(pagination is not supported)

GET /customers/{Customer_Ref}

returns a customer with the given ref or 404:Not_Found if no entity exists with the given ref
