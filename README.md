**Microservices assignment 2023**

**Booking System**

**Table of contents\
**

  Topic Name                                          Page Number
  --------------------------------------------------- -------------
  Booking System High Level Design                    1
  Identified Microservices with explanation           2
  API endpoints with explanation                      3
  Successful and Failure Flow during Flight booking   13
  Successful and Failure Flow during Hotel booking    18

**\
\
**

**\
**

**Booking System High Level Design**

![](media/image1.png){width="7.268055555555556in" height="8.6125in"}

**\
**

**Identified Microservices with explanation.**

1.  **Identity Service** (identity-service)**\
    **This deals with user related operations such as registering a new
    user, log in the user and provide a valid authentication token. The
    user details along with a valid authentication token is required to
    create a successful booking.

2.  **Flights Database Service** (db-flights-service)**\
    **This service is created to act as a database that holds
    flights data. This service provides the capability to read flights
    in the database. This service updates the flights in the database
    according to bookings created.

3.  **Flights Service** (flights-service)**\
    **This service is created to provide abstraction to Flights
    Database Service. As Database can have confidential data related to
    flights so it cannot be exposed to the public. Therefore, Flights
    Service deals with user operations and provides the user only the
    data that is important to the user.

    It allows authenticated user to search flights by defining options
    like source location, destination location, departure
    date, flightId.

4.  **Hotels Database Service** (db-hotels-service)

    This service is created to act as a database that holds Hotels data.
    This service provides the capability to read hotels in the database.
    This service updates the hotels in the database according to
    bookings created.

5.  **Hotels Service** (hotels-service)**\
    **This service is created to provide abstraction to Hotels
    Database Service. As Database can have confidential data related to
    hotels so it cannot be exposed to the public. Therefore, Hotels
    Service deals with user operations and provides the user only the
    data that is important to the user.

    It allows authenticated user to search hotels by defining options
    like hotel name, city, hotel address.

6.  **Booking Service** (booking-service)\
    This service is created to create bookings related to flights
    and hotels. This service keeps tracks of all the transactions
    related to particular booking and updates it in the booking order
    stored in it.\
    This service allows authenticated user to create a new booking and
    view the booking order details.

7.  **Payments Service** (payments-service)\
    This service is used to mock user payment requests. This service
    provides functionality to mock a successful payment request and also
    to mock a failed payment request. These two scenarios are important
    to showcase fault tolerance features of the system.

8.  **Booking Saga Orchestrator** (booking-saga-orchestrator)\
    This service is based on saga orchestrator pattern in microservices.
    This service manages all the asynchronous communication that is
    happening between different microservices. This service coordinates
    the events in a systematic manner according to transactional flow
    required for the booking system.

9.  **Discovery Server** (discovery-server)\
    This service is a Eureka Discovery Server which helps the various
    microservices to communicate with each other. This service removes
    the necessity for a microservice to know the actual address of other
    microservice to communicate with it. All the microservices that are
    meant to communicate with each other are registered to discovery
    server as discovery clients.

**\
**

**API endpoints with explanation**

**Import the below mentioned file located in the project directory for
automatically creating routes in Postman application**

Api Gateway Routes - Booking System

![](media/image2.png){width="3.215442913385827in"
height="1.0208858267716536in"}

1.  **Api gateway endpoints:\
    **This service is used to map endpoints of different microservices
    under a single domain.\
    \
    Base Uri: <http://localhost:8001>\
    Various microservices are mapped to api gateway as show below

    a.  **Register a new user ( identity-service)**\
        Uri : <http://localhost:8001/users>\
        Method : Post\
        All fields are required\
        Request Body : JSON\
        <span id="_MON_1741617796" class="anchor"></span>**\
        \
        **Result :\
        <span id="_MON_1741618361" class="anchor"></span>

    b.  **Login user (identity service)**\
        Uri : <http://localhost:8001/users/login>\
        Method : Post\
        All fields are required\
        Request Body : JSON\
        <span id="_MON_1741618522" class="anchor"></span>**\
        \
        **Result :\
        <span id="_MON_1741618548" class="anchor"></span>

    c.  **Search Flight (filghts-service)\
        **Uri :
        http://localhost:8001/flights?source=Amritsar&destination=Delhi&departureDate=24-04-2023\
        Method : Get\
        *Authentication is Required*, kindly provide valid token under
        Bearer Token in Postman.\
        \
        Query params (all query params are optional, if not provided all
        flights will be shown)\
        1. source = name of the source city\
        2. destination = name of the destination city\
        3. departureDate = date of travel must be in DD-MM-YYY format\
        \
        Request\
        ![](media/image7.png){width="5.905511811023622in"
        height="1.7677165354330708in"}\
        \
        Result\
        ![](media/image8.png){width="5.905511811023622in"
        height="3.326771653543307in"}

    d.  **Search a specific flight (flights-service)**\
        Uri :
        [http://localhost:8001/flights/{flightd}](http://localhost:8001/flights/%7bflightd%7d)\
        Method : Get\
        *Authentication is Required*, kindly provide valid token under
        Bearer Token in Postman.\
        \
        Path Variable (required)\
        1. flightId = Flight Id of a flight.\
        \
        \
        \
        \
        \
        \
        \
        \
        \
        \
        Request\
        ![](media/image9.png){width="5.905511811023622in"
        height="1.9606299212598426in"}\
        \
        Response\
        ![](media/image10.png){width="5.905511811023622in"
        height="3.3346456692913384in"}

    e.  **Book a Flight (booking-service)\
        **Uri : http://localhost:8001/booking/flights\
        Method : Post\
        *Authentication is Required*, kindly provide valid token under
        Bearer Token in Postman.\
        \
        Request Body : Json (All fields required)\
        1. username = username of the user booking flight\
        2. flightId = Flight Id of the flight to be booked\
        3. seatNumbers = Array of seat numbers user wish to book. (seats
        available 1 to 30)\
        <span id="_MON_1741630329" class="anchor"></span>\
        \
        \
        \
        \
        \
        \
        \
        \
        Request:**\
        **![](media/image12.png){width="5.905511811023622in"
        height="1.8626115485564305in"}**\
        **Response:\
        ![](media/image13.png){width="5.905511811023622in"
        height="2.8661417322834644in"}

    f.  **Check Flight Booking status (booking-service)\
        **Uri : http://localhost:8001/booking/flights/{bookingId}\
        Method : Get\
        *Authentication is Required*, kindly provide valid token under
        Bearer Token in Postman.\
        \
        Path Variable (required)\
        1. bookingId = bookingId is unique id to specify the booking
        order.\
        \
        Request:\
        ![](media/image14.png){width="5.905511811023622in"
        height="1.7007874015748032in"}\
        \
        \
        \
        \
        \
        \
        \
        \
        Response:\
        ![](media/image15.png){width="5.905511811023622in"
        height="2.1338582677165356in"}

    g.  **Send Payment (payment-service)\
        **It is payment mocking service and can be used to perform two
        tasks\
        1. To mock payment success\
        2. To mock payment failure\
        \
        **I. Flights Payment Success Mocking\
        **Uri : http://localhost:8001/payments/flights/{bookingId}\
        Method : Post\
        *Authentication is Required*, kindly provide valid token under
        Bearer Token in Postman.\
        \
        Path Variable (required)\
        1. bookingId = bookingId is unique id to specify the booking
        order.\
        \
        Request:\
        ![](media/image16.png){width="5.905511811023622in"
        height="1.6574803149606299in"}\
        \
        Response:\
        ![](media/image17.png){width="5.905511811023622in"
        height="1.2165354330708662in"}\
        \
        \
        2. **Flights Payment Failure Mocking\
        **Uri :
        http://localhost:8001/payments/flights/{bookingId}?fail=true\
        Method : Post\
        *Authentication is Required*, kindly provide valid token under
        Bearer Token in Postman.\
        \
        Path Variable (required)\
        1. bookingId = bookingId is unique id to specify the booking
        order.\
        \
        To make the payment fail add the following query parameter in
        the URI.\
        Query Param (Required)\
        1. fail=true\
        \
        Request:\
        ![](media/image18.png){width="5.905511811023622in"
        height="1.633523622047244in"}\
        \
        Response:\
        ![](media/image19.png){width="5.905511811023622in"
        height="1.36625656167979in"}

    h.  **Search Hotels (hotels-service)\
        **Uri:
        http://localhost:8001/hotels?hotelName=Hyatt&city=Amritsar&address=Ranjit
        Avenue, Amritsar\
        Method: Get\
        *Authentication is Required*, kindly provide valid token under
        Bearer Token in Postman.\
        \
        Query params (all query params are optional, if not provided all
        flights will be shown)\
        1. hotelName = name of the hotel\
        2. city = name of the hotel city\
        3. address = complete address of a hotel\
        \
        Request:\
        ![](media/image20.png){width="5.905511811023622in"
        height="2.1272484689413824in"}\
        \
        \
        \
        \
        \
        \
        Result:\
        ![](media/image21.png){width="5.905511811023622in"
        height="2.2998556430446193in"}

    i.  **Search a specific hotel (hotels-service)**\
        Uri : http://localhost:8001/hotels/HY4139\
        Method : Get\
        *Authentication is Required*, kindly provide valid token under
        Bearer Token in Postman.\
        Path Variable (required)\
        1. hotelId = Hotel Id of a hotel.\
        \
        Request\
        ![](media/image22.png){width="5.905511811023622in"
        height="2.0945209973753283in"}\
        \
        Response\
        ![](media/image23.png){width="5.905511811023622in"
        height="2.6055752405949257in"}

    j.  **Book a Hotel (booking-service)\
        **Uri : http://localhost:8001/booking/hotels\
        Method : Post\
        *Authentication is Required*, kindly provide valid token under
        Bearer Token in Postman.\
        \
        Request Body : Json (All fields required)\
        1. username = username of the user booking flight\
        2. hotelId = Hotel Id of the hotel to be booked\
        3. checkInDate = Check In Date (required date format is
        DD-MM-YYYY)\
        4. checkOutDate = Check Out Date (required date format is
        DD-MM-YYYY)\
        5. roomsRequired = Number of rooms to be booked\
        <span id="_MON_1741727762" class="anchor"></span>**\
        \
        **Request:\
        ![](media/image25.png){width="5.905511811023622in"
        height="2.3422298775153108in"}**\
        \
        **Response :\
        ![](media/image26.png){width="5.905511811023622in"
        height="2.2711340769903763in"}

    k.  **Check Hotel Booking status (booking-service)\
        **Uri : http://localhost:8001/booking/hotels/{bookingId}\
        Method : Get\
        *Authentication is Required*, kindly provide valid token under
        Bearer Token in Postman.\
        \
        Path Variable (required)\
        1. bookingId = bookingId is unique id to specify the booking
        order.\
        \
        \
        \
        Request:\
        ![](media/image27.png){width="5.905511811023622in"
        height="1.9935192475940506in"}\
        \
        Response:\
        ![](media/image28.png){width="5.905511811023622in"
        height="2.527306430446194in"}

    l.  **Send Payment (payment-service)\
        **It is payment mocking service and can be used to perform two
        tasks\
        1. To mock payment success\
        2. To mock payment failure\
        \
        **I. Hotels Payment Success Mocking\
        **Uri : http://localhost:8001/payments/hotels/{bookingId}\
        Method : Post\
        *Authentication is Required*, kindly provide valid token under
        Bearer Token in Postman.\
        \
        Path Variable (required)\
        1. bookingId = bookingId is unique id to specify the booking
        order.\
        \
        Request:\
        ![](media/image29.png){width="5.905511811023622in"
        height="2.2874967191601048in"}\
        \
        Response:\
        ![](media/image17.png){width="5.905511811023622in"
        height="1.2165354330708662in"}\
        \
        2. **Hotels Payment Failure Mocking\
        **Uri :
        http://localhost:8001/payments/hotels/{bookingId}?fail=true\
        Method : Post\
        *Authentication is Required*, kindly provide valid token under
        Bearer Token in Postman.\
        \
        Path Variable (required)\
        1. bookingId = bookingId is unique id to specify the booking
        order.\
        \
        To make the payment fail add the following query parameter in
        the URI.\
        Query Param (Required)\
        1. fail=true\
        \
        Request:\
        ![](media/image18.png){width="5.905511811023622in"
        height="1.633523622047244in"}\
        \
        Response:\
        ![](media/image19.png){width="5.905511811023622in"
        height="1.36625656167979in"}

        **Successful and Failure Flow during Flight booking\
        \
        **

<!-- -->

1.  **Successful flow flight booking\
    **\
    Follow the following steps to create a successful booking.

<!-- -->

1.  Register a new user.\
    ![](media/image30.png){width="5.511811023622047in"
    height="2.8038298337707785in"}

2.  Login and get a valid token to carry out further requests.\
    ![](media/image31.png){width="5.511811023622047in"
    height="2.7406332020997377in"}

3.  Place the valid token under\
    Authorization =&gt; Type =&gt; Bearer Token field in Postman
    application for the below mentioned requests as these are protected
    routes.\
    ![](media/image32.png){width="5.511811023622047in"
    height="1.3571500437445319in"}

4.  Search Flights (for example lets search for flights from Amritsar to
    Delhi)\
    ![](media/image33.png){width="5.511811023622047in"
    height="1.8442924321959755in"}\
    \
    ![](media/image34.png){width="5.511811023622047in"
    height="2.5231310148731407in"}

5.  Copy the flightId of the flight whose complete details you wish to
    find i.e. which seats are available.\
    let’s use the flightId = AB1234 for further transactions.

6.  Using flightId get details of a flight.\
    ![](media/image35.png){width="5.511811023622047in"
    height="3.1171806649168854in"}

7.  See which seats are available and choose the ones to book.

8.  Request to create a booking as shown below.\
    Let’s book seats \[1,2,3\]\
    \
    You will get your booking details in response where the initial
    status of the booking will be PROCESSING.\
    ![](media/image36.png){width="5.511811023622047in"
    height="1.6167836832895888in"}\
    \
    ![](media/image37.png){width="5.511811023622047in"
    height="2.2993088363954506in"}\
    Copy the bookingId to get further updates on your booking order.

9.  Check booking order status using bookingId.\
    ![](media/image38.png){width="5.511811023622047in"
    height="2.886512467191601in"}\
    Here you will see the PAYMENT\_PENDING status.

10. Proceed to payment using the same bookingId or copy the payment url
    shown in booking.\
    ![](media/image39.png){width="5.511811023622047in"
    height="1.9469870953630797in"}\
    On successful payment, you will see payment is successful in
    response and your booking status will be updated accordingly.

11. Now after successful payment the application will again confirm
    whether the required seats are available or not. If seats are
    available then the seats will be marked booked and booking status
    will then be updated to Confirmed.\
    \
    Check booking once again.\
    ![](media/image40.png){width="5.511811023622047in"
    height="2.874925634295713in"}\
    This how a successful flight booking flow will work.

**\
**

1.  **Negative/Failure flow flight booking.\
    \
    **There can be two scenarios leading to failure of booking when all
    the services are working i.e. below mentioned failures does not
    occurs when some of the services are down.\
    \
    1. User try to book seats which are already booked before making
    payments.\
    ![](media/image41.png){width="5.511811023622047in"
    height="2.800143263342082in"}\
    \
    2. A case when two users have created booking for same seats in a
    flight but have not proceed with payments. In this case the bookings
    will be created for both users.\
    But say user1 proceeds with payment first, then user1 will be able
    to book successfully.\
    But now when user2 proceeds with payments, the payments will be
    successful but then the application will notice that the seats
    requested by user2 have been booked already. So in this case the
    seats can’t be booked therefore a payment refund event will be
    generated for user2 and user2’s booking will be marked unconfirmed
    with suitable remarks.\
    \
    ![](media/image42.png){width="5.511811023622047in"
    height="2.4320220909886263in"}

**\
**

**Successful and Failure Flow during Hotel booking\
\
**

1.  **Successful flow hotel booking\
    **\
    Follow the following steps to create a successful booking.

<!-- -->

1.  Register a new user.\
    ![](media/image30.png){width="5.511811023622047in"
    height="2.8038298337707785in"}

2.  Login and get a valid token to carry out further requests.\
    ![](media/image31.png){width="5.511811023622047in"
    height="2.7406332020997377in"}

3.  Place the valid token under\
    Authorization =&gt; Type =&gt; Bearer Token field in Postman
    application for the below mentioned requests as these are protected
    routes.\
    ![](media/image32.png){width="5.511811023622047in"
    height="1.3571500437445319in"}

4.  Search hotels (for example lets search for Hotels in Amritsar)\
    ![](media/image43.png){width="5.511811023622047in"
    height="2.162382983377078in"}\
    \
    ![](media/image44.png){width="5.511811023622047in"
    height="3.0708661417322833in"}

5.  Copy the hotelId of the hotel whose complete details you wish to
    find i.e. what are total number of rooms and price of a single
    room.\
    let’s use the hotelId = HY4139 for further transactions.

6.  Using hotelId get details of a hotel.\
    ![](media/image45.png){width="5.511811023622047in"
    height="2.862286745406824in"}

7.  Request to create a booking as shown below.\
    Let’s book seats 2 rooms where check-in date is 01-04-2023 and
    check-out date is 03-04-2023\
    \
    You will get your booking details in response where the initial
    status of the booking will be PROCESSING.\
    ![](media/image46.png){width="5.511811023622047in"
    height="2.111825240594926in"}\
    \
    ![](media/image47.png){width="5.511811023622047in"
    height="2.3261679790026246in"}\
    Copy the bookingId to get further updates on your booking order.

8.  Check booking order status using bookingId.\
    ![](media/image48.png){width="5.511811023622047in"
    height="2.9770942694663165in"}\
    Here you will see the PAYMENT\_PENDING status.

9.  Proceed to payment using the same bookingId or copy the payment url
    shown in booking.\
    ![](media/image49.png){width="5.511811023622047in"
    height="2.1007655293088363in"}\
    On successful payment, you will see payment is successful in
    response and your booking status will be updated accordingly.

10. Now after successful payment the application will again confirm
    whether the required rooms are available or not. If rooms are
    available then the rooms will be allocated to user and booking
    status will then be updated to Confirmed.\
    \
    Check booking once again.\
    ![](media/image50.png){width="5.511811023622047in"
    height="2.9054713473315834in"}\
    This how a successful hotel booking flow will work.

**\
**

1.  **Negative/Failure flow hotel booking.\
    \
    **There can be two scenarios leading to failure of booking when all
    the services are working i.e. below mentioned failures does not
    occurs when some of the services are down.\
    \
    1. User try to book rooms and the required number of rooms are not
    available before making payments.\
    ![](media/image51.png){width="5.511811023622047in"
    height="2.375145450568679in"}\
    \
    2. Consider a scenario when 5 rooms are left in a hotel. Now two
    users create a booking for 5 rooms in the hotel individually. The
    bookings will be created respective to each user as the payment is
    not completed by any of them yet so the rooms have not
    been allotted. Now say user-1 proceeds with payment first so user-1
    will be allotted the rooms and its booking will be confirmed.\
    Now after that user-2 proceeds with payment, the payment will be
    successfully received but then the application will verify if the
    rooms are available or not before making booking. In this case the
    remaining rooms have been allotted. So the application will generate
    a refund process and update this in the booking order and the status
    of the booking order for user-2 will be marked UNCONFIRMED.\
    \
    ![](media/image52.png){width="5.905511811023622in"
    height="2.4742661854768153in"}
