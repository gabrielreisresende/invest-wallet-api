# Invest Wallet - API
[![NPM](https://img.shields.io/npm/l/react)](https://github.com/gabrielreisresende/invest-wallet-api/blob/main/LICENSE)

## Description
This is a REST API designed for controlling your investments in 'Fundos Imobiliários' (Real Estate Funds).
The API enables users to have multiple wallets and manage them efficiently.
With Invest Wallet, you can oversee each asset in your wallet,
generate three distinct reports related to your wallets, and use the data to create a dashboard for comprehensive analysis.
Also, you can download this wallet report or receive the pdf in your email.

--------------------------------------------------------------------------------------------------------------
## Swagger
Link to test and view all endpoints from Invest Wallet:
```
http://localhost:8080/api/v1/swagger-ui/index.html#/
```

## Technologies
- Java 17;
- Spring Boot 3.2.2;
- MySQL;
- Postman.

## Security
- Spring Security for authorization and authentication;
- JWT authentication;
- Two users role: CLIENT and ADMIN;
- Login data encrypted with Basic Auth;
- Endpoints with authorization required;
- Forgotten password proccess.

### Admin permissions
- Manage asset codes;
- Manage asset types;
- Manage asset sectors.

### Client functionalities
- Register in the system;
- Manage personal data;
- Create anda manage multiple wallets;
- Manage wallet assets;
- Generate reports about each wallet;
- Download a pdf with your detailed wallet report;
- Receive the wallet report in your email.

### Email functionalities
- Send an welcome email after the user registration;
- Send an email with Two Factor Code authentication during the Reset Password Process;
- Send an email with the Wallet Report pdf.
--------------------------------------------------------------------------------------------------------------
#### Example of a Wallet Report generated:
![image](https://github.com/gabrielreisresende/invest-wallet-api/assets/123999571/f52caa8e-bfee-4b11-9a2e-8a0a4f17fdf9)

--------------------------------------------------------------------------------------------------------------
#### Email message with the wallet report pdf:
![image](https://github.com/gabrielreisresende/invest-wallet-api/assets/123999571/bb3cdc59-c3e1-4ae3-8fbf-777d98bf530d)

--------------------------------------------------------------------------------------------------------------

## Run Invest Wallet Application
Firstly, clone the repository:

```
https://github.com/gabrielreisresende/invest-wallet-api.git
```

Once done, navigate to the project:

```
cd invest-wallet-api
```

You need to compile the code and download the project dependencies:

```
mvn clean package
```

After completing this step, let's start the application:

```
mvn spring-boot:run
```

There you go. The application is now available at `http://localhost:8080`.

```
Tomcat started on port(s): 8080 (http)
Started AppConfig in xxxx seconds (JVM running for xxxx)
```


## Enviroment variables
Configure the environment variables according to your data to run the application. The application uses the following environment variables:

```
- ${MYSQL_USER}
- ${MYSQL_PASS}
- ${EMAIl_USER}
- ${EMAIl_PASS}
```
  
