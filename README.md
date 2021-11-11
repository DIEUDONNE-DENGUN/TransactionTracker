##Project Objective

     Design and implement a transaction tracking system

##Introduction

    IOTA is an open-source distributed ledger and cryptocurrency designed for the Internet of things. 
    It uses a directed acyclic graph to store transactions on its ledger, motivated by a potentially higher scalability over blockchain based distributed ledgers.

    IOTA does not use miners to validate transactions, instead, nodes that issue a new transaction on the network must approve two previous transactions.
    Transactions can therefore be issued without fees, facilitating microtransactions 

##Getting Started
    The transaction tracking application is a web based application with a backend running as a RESTFUL API service and the frontend running as a an angular web application.
    The application backend makes lots of use of dependency injection and the "program to an interafce" design principle.

    Backend application has both Unit Tests and Integration Tests for its core component. List under the test spring boot directory
    Communication between the frontend and backend is over TCP/REST using an authorization security key exchange

    These instructions will get you a copy of the project up and running on your local machine 
    for development and testing purposes.
    See deployment section for notes on how to deploy the project on an apache  system.  
##Installation Dependencies
* Java 8
* Mysql server
* Maven 3
* Npm/Node for frontend
* Docker Compose
* Nginx Sever
  
##Application Components
      
   * REST API Interface
   * Transaction Generator and Scheduler Service 
   * Web Application Client Interface

##Deployments
    Using docker compose, all application services for both the frontend and backend are deployed seamlessly.
    The application zip contains the following files/directories:

     1) A docker-compose.yml file which has the configuration for all 3 main services, backend, frontend and mysql db
     2) A README.md file which is this file that has installation documentation and project details
     3) A TransactionTrackerApi directory which contains the API and Scheduler Backend Spring boot project (codes) and its associated Dockerfile
     4) A TransactionTrackingWeb directory which contains the frontend Angular project (codes) and its associated Dockerfile


    To build and compose the docker container for all the application service follow the steps below:

    1) After zipping the appication zip file, you should be in the current directory to view this README
    2) Open your terminal or command line interface and cd or navigate this to project main directory
    3) Run this command:  docker-compose build  (this command will grab all application dependencies and build its respective docker images)
    4) If step 3 was successful, Run the command: "docker-compose --verbose up"  (to build and run all backend images and to show verbose output on the terminal)
    5) If step 4 went error-free, Kindly open any your favorite browser and navigate to http://localhost:8081 to access the Web UI *(Backend service already running if step 4 was successful)

##Deployment Assumptions
      
* Application docker compose configuration points to local host for both the frontend and backend within the docker engine
* It is assumed that MYSQL is not running on the host computer on port 3306 (Mysql Docker service uses this for the run the db)
* Again, since we are using Nginx server to run the frontend service or image, it is assumed the host computer port 80 is currently free and can be used.
* All application Units and Integration Backend Tests should have ran after running the command: docker-compose --verbose up 
  

##Built With
###Backend Technologies
     Spring boot framework 2.5.5 / Java JDK 1.8.2
     Spring Secuirty/Filter for api authentication
     Spring WebSocket/Stompjs for realtime transaction publishing
     MySQL Database /Hibernate JPA
     Springfox Swagger for api documentation
###Frontend Technologies
     Angular framework 9
     Bootstrap for UI components
     Jquery 3.6.0
     Sockjs-client/stompjs to add realtime websocket layer

##Last Thought
    Thank you for taking the time in testing the project. Would appreciate feedback
    Just in case of any challenge in deploying and traversing the application codes 
    Please kindly write me, be sure of my responsiveness in case of any questions, worries or need explanation
    
    Best Regards

## Author

     Dieudonne Takougang

