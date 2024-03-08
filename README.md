# Invest Wallet - API
[![NPM](https://img.shields.io/npm/l/react)](https://github.com/gabrielreisresende/invest-wallet-api/blob/main/LICENSE)

## Description
This is a REST API designed for controlling your investments in 'Fundos Imobiliários' (Real Estate Funds).
The API enables users to have multiple wallets and manage them efficiently.
With Invest Wallet, you can oversee each asset in your wallet,
generate three distinct reports related to your wallets, and use the data to create a dashboard for comprehensive analysis.

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
- Two users role: CUSTOMER and ADMIN;
- Login data encrypted with Basi Auth;
- Endpoints with authentication.
  
### Admin permissions
- Manage asset codes;
- Manage asset types;
- Manage asset sectors.

### Customer functionalities
- Register in the system;
- Manage personal data;
- Create anda manage multiple wallets;
- Manage wallet assets;
- Generate reports about each wallet.

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
```
  
