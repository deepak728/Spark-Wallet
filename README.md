# Spark-Wallet

Spark wallet let user add and transfer money easily.This repository has seven api for creating new user account, login, delete user, update user, get User details, add money and transfer money. A dummy bank is also integrated with it for tranactions.

# About the Applicaton

I have used Intellij IDEA as an editor, all the api is developed using java, xml, sql, json. Application is also dockerized which runs on tomcat server inside the container (port 8080). User may also run it locally on jetty Server (port 8090).

# Api-Info

1. Create User (POST Api) : It allows user to create their account with all the details                                         (firstName,lastName,userName,phone,email,password).Each user has unique phone no which cannot be updated. A dummy bank account corresponding to each phone no is also created along with the user account. User will get Json response of created account details.
     
       api url (jetty) : http://localhost:8090/spark_wallet/api/spark/add
        
        Json Request
	
	```json

       {   
        "firstName":"deepak",
        "lastName":"kumar",
        "userName":"dk",
        "phone":"1234567890",
        "email":"dk@gmail.com",
        "password":"dk@908"
        
	   }
	```
       

2. Update User (POST Api) : It allows User to update their information (except phone no). User will get Json response of User updated details.
       
       api url (jetty) : http://localhost:8090/spark_wallet/api/spark/update
        
	    Json Request
	    
```json
            {   
        "firstName":"deepak",
        "lastName":"kumar",
        "userName":"dkumar",
        "phone":"1234567890",
       "email":"dkumar@gmail.com",
       "password":"dk@728"
           }
```
	   
3. Login (POST Api) : User can login to their account using their phone no and password. User will get token in the header as response with expiry time of 24hrs.

       api url (jetty) : http://localhost:8090/spark_wallet/api/spark/login
       
        Json Request
	
```Json
       { 
        "ph":"1234567890",
       "passWord":"dk@728"
       }
```
       
4. Get User Details (GET Api) : User can get all their information using the token provided during login.

       api url (jetty) : http://localhost:8090/spark_wallet/api/spark

5. Delete User (DELETE Api) : User can delete their account using the token provided during login. 
     
       api url (jetty) : http://localhost:8090/spark_wallet/api/spark
       
6. Add Money (POST Api) : User can add  money to their wallet from their corresponding bank account using token. 
      
        api url (jetty) : http://localhost:8090/spark_wallet/api/spark/addMoney
	
            Json Request
	   
```Json
        { 
       "amount":2000
        }
```
        
 7. Send Money (POST Api) : User can send money from their wallet to another user wallet using token and their phone no .If sufficient amount is not present in user's wallet then required amount will be added to the user's wallet from their bank account. 
 
        api url (jetty) : http://localhost:8090/spark_wallet/api/spark/sendMoney
	  
          Json Request
	  
```Json
         {   
	          "receiverPh":"0987654321",
	          "amount":1000
          }
```      
# Requirements 

Java jdk 8

Gradle 3.5 

PostMan

Mysql

Docker(Optional)


# Setup and Running the application

1.Run sql server then enter following command.

         CREATE DATABASE spark;
         USE spark;
         CREATE TABLE User( Id integer not null auto_increment, firstName varchar(20), lastName varchar(20), userName varchar(20), email varchar(20), phone varchar(10), password varchar(32), wallet float, access_key varchar (20),token varchar (32), expiry_time date);
         CREATE TABLE UserAccount(id integer not null auto_increment,account_id integer,balance float);

2. Set Java 8 and gradle 3.5 in the path and build the application.
            
           gradle clean build 
           
           
3. Run the application locally on jetty server (port 8090)

           gradle clean build jettyRun

4. Test api using postman 
                
            import all api on postman :  https://www.getpostman.com/collections/1e647835aca6475c0091
            
            
 Running application in docker
 
 
 1. Install docker and run 
           
           docker build -t spark_wallet --build-arg GRADLE_VERSION=3.5 --build-arg TOMCAT_SUB_VERSION=9.0.8 --build-arg TOMCAT_VERSION=9 --build-arg SERVICE_NAME=spark_wallet --build-arg PORT=8080 --no-cache .
 
 2. Get application docker image_id 
               
         docker images
 
 3. Run the application 
         
         docker run -p 8080:8080 image_id
 
 4. Test api using postman 
 
        http://localhost:8080/spark_wallet-1.0/api/spark/(.....)
