# **Real Estate Back-End Web Application**
A robust and scalable back-end application designed for managing real estate properties, agents, and their interactions. Built with Java, Spring Boot, and MySQL, this project incorporates modern technologies to deliver a seamless experience for administrators and users.

![Real Estate](https://img.shields.io/badge/Real%20Estate-App-blue)
![Java](https://img.shields.io/badge/Java-11%2B-red)
![JWT](https://img.shields.io/badge/JWT-JSON%20Web%20Token-blue)
![Docker](https://img.shields.io/badge/Docker-20%2B-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.0%2B-green)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-2.0%2B-orange)
![Spring Validation](https://img.shields.io/badge/Spring%20Validation-5.0%2B-blue)
![Spring Security](https://img.shields.io/badge/Spring%20Security-5.0%2B-blue)
![Swagger](https://img.shields.io/badge/Swagger-3.0%2B-blue)
![Prometheus](https://img.shields.io/badge/Prometheus-2.0%2B-blue)
![Spring Mail](https://img.shields.io/badge/Spring%20Mail-1.0%2B-green)
![Resilience4j](https://img.shields.io/badge/Resilience4j-1.0%2B-green)
![MySQL](https://img.shields.io/badge/MySQL-5.7%2B-blue)
![AWS](https://img.shields.io/badge/AWS-2.0%2B-orange)

### **Table of Contents**
- Features
- Technologies Used
- Getting Started
- Configuration
- Testing
- Contributing
- License



### **Features**
- **Property Management**: Add, update, delete, and view property listings.
- **Agent Management**: Manage agents including their bios, contact details, and licenses.
- **JWT Authentication**: Secure endpoints with JSON Web Tokens.
- **Swagger API Documentation**: Interactive API exploration and testing.
- **Monitoring**: Built-in application metrics using Prometheus.
- **Rate Limiting**: Protect endpoints with Resilience4j.
- **Email Notifications**: Notify agents or clients with email integration.



### **Technologies Used**
- Java 17
- Spring Boot 3
- Spring Data JPA
- Spring Security
- Spring Validation
- Spring Mail
- MySQL for database
- JWT for authentication
- Swagger OpenAPI for API documentation
- Prometheus for monitoring
- Resilience4j for rate limiting



### **Getting Started**
1. Prerequisites
  - Java 17 or later
  - Maven (for dependency management)
  - MySQL (Database)
  - Postman (for API testing)
2. Clone the Repository
```
git clone https://github.com/stephenombuya/RealEstate-Backend-Web-Application/tree/main
cd real-estate-backend
```
3. Install Dependencies
```
mvn clean install
```
4. Run the Application
- Update the application.properties file with your database credentials and configuration.
- Start the application:
```
mvn spring-boot:run
```



### **Configuration**
Update the following in src/main/resources/application.properties:

```
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/db_name
spring.datasource.username=your_username
spring.datasource.password=your_password

# JWT Configuration
jwt.secret=your-secret-key
jwt.expiration=86400000

# Email Configuration
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email
spring.mail.password=your-email-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```



### **Testing**
- Run the test suite with:

```
mvn test
```
- Testing Features
  - Unit and integration tests for services and controllers.
  - Authentication and authorization tests.
  - Database validation tests.



### **Contributing**
We welcome contributions! Follow these steps to contribute:

1. Fork the repository.
2. Create a feature branch:
```
git checkout -b feature-name
```
3. Commit your changes:
```
git commit -m "Add new feature"
```
4. Push to your branch:
```
git push origin feature-name
```
5. Open a pull request.


### **License**
This project is licensed under the GNU License. See the `LICENSE` file for details.

