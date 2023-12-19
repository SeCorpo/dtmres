# Client structure documentation

- Every html should have a simple controller? 
- only the controller-class is mentioned as script in html?
- all CRUD operations should be in Service classes?

### js / model
Is used to make objects from received data.  (functions are used by controller
and service)


### js / service
Here are all server communication-functions stored (CRUD operations),
service also casts received data into models.
(functions are used by js/controller)


### js / view
Dealing with displaying objects for the controller, also handling user input
(functions are used by the controller)


### js / controller
The frame for the html, all 'normal' script for support of the html,
e.q. listeners, show/ hide functions...
Uses js/model and js/service to control input from the database,
uses js/view to display the received data


### js main
This class keeps the session, most operations go through the session.
If user logs in the session is linked to the user in table sessions.
getSession is one of the most used functions.


### js storageManager
Manages current local reservations (before login, session storage),
until reservations are made and in database

## Example:
Opening the website, loading all products available for user-reservation

main receives a session from the server, stores in new cookie
(and checks if there is not a current session)
1. index.html is loaded.
   productController makes request to productService to get all products (init function)
2. productService passes raw data to productController
3. productController casts data in multiple productModel
4. productController stores list of productModel-objects
5. productController casts productModel-object to productView-display function
6. productController 'prints' all (productModel-objects as)
   productView-display-functions to user

Visual:
![loading products to index.jpg](loading%20products%20to%20index.jpg)
Controller > Service > Controller > Model > Controller > View > Controller

