# This app was compiled using jdk 11.0.2, apache maven 3.6.0 and IntelliJ Community Edition 2018.3.4

In case of compile error please try using jdk 11.0.2 or later and apache maven 3.6.0

# Assumptions
---
1. The best seats are determined by the id only.
2. Hold time for the seats is 60 seconds. after that the seats will go back to "OPEN" state.
3. Confirmation code is a letter C followed by Hold Id and my name.

# Building Project

1.   Clone the project
	
	git clone https://github.com/Darnek/ticket-service-walmart.git
	
2.   Using your prefered IDE run the following command

**	mvn package

3.   Run the Main class, you should see something like 
	
	.... Tomcat started on port(s): 8080 (http)
	.... Started Main in 2.191 seconds (JVM running for 2.592)
	
4.   To test you should run the command "mvn test" or click run on the TicketTest.java

5.   To test the services using angular you need to have installed node.js, angular7 and enter the folder 'ticket-walmart-client' and run 'ng s',
	this will start the server on the port 4200

	http://localhost:4200

     The app is very basic but allows you to hold and register seats, notes for use are on the webpage, validations and improvements to UI will come in near future.

6. You can also check the status of the services using swagger on the following url http://localhost:8080/swagger-ui.html


# REST Web Services
---

1.	To find the seats and status use the following in your browser or restclient.
	
	GET - http://localhost:8080/seats

2.	To find the number of seats available use the following in your browser or restclient.
	
	GET - http://localhost:8080/seats/number
		```		
3.	Find and hold the best available seats on behalf of a customer
	
	POST - http://localhost:8080/seats/hold
	
	RequestBody:
	{
        "numSeats": 3,
        "customerEmail": "xdarnek@gmail.com"
	}
	
	Response:
	
	{
    "id": 1,
    "customerId": 0,
    "holdTime": "2019-02-08T12:27:58.734+0000",
    "customerEmail": "xdarnek@gmail.com",
    "seatsHolded": [
        A1,
        A2,
        A3,
        ]
	}
	
	The request will expire after 60 seconds. Before that, the user has to reserve the seats using the web service in the following request.
	
4.	Reserve and commit (using the id from previous response)

	POST - http://localhost:8080/seats/reserve
	
	RequestBody:
{
        "seatHoldId": 1,
        "customerEmail": "xdarnek@gmail.com"
}
	
	Response: (Confirmation Code)
	
	C[randomhash] e.g. C1254879464
	
5.      If you want to see the numbers holded by your id

	GET - http://localhost:8080/seats/seatsholded/{id}

	where id is your id, example: http://localhost:8080/seats/seatsholded/1

	response
	
[
    A1,
    A2,
    A3,
    A4,
    A5,
    A6,
    A7,
    A8,
    A9,
    A10
]


5.	In case that you only want to reserve some of the holded seats (using the id from previous response)

	POST - http://localhost:8080/seats/reserveList
	
	RequestBody:
{
        "seatHoldId": 1,
	 "seatsList": [
        A1,
        A2,
        A3
	],
        "customerEmail": "xdarnek@gmail.com"
}
	
	Response: (Confirmation Code)
	
	
	C[randomhash] e.g. C1254879464
	


6.      Swagger added, to see available methods and parameters received.

	To access go to http://localhost:8080/swagger-ui.html
