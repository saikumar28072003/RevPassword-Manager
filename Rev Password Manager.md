Description:
A console-based password manager application developed using Java and JDBC that allows users to securely manage account passwords with authentication, OTP verification, and security questions.

Technologies Used:
Java (JDK 7)
JDBC
Oracle Database
JUnit
Mockito
SLF4J
reload4j (Log4j backend)

🔹 Project Setup
1️. Prerequisites

JDK 7 installed
Oracle Database (10g)
Eclipse IDE for java developers

2️. Database Setup

Create Oracle user/schema
Run provided SQL scripts to create tables:
USERS
PASSWORD_ENTRIES
SECURITY_QUESTIONS
OTP

3️. Project Setup in Eclipse

create Java Project in enclipse
Add required JAR files:
JDBC driver (ojdbc)
JUnit
Mockito
SLF4J
reload4j (log4j backend)

4️. Logging Configuration

log4j.properties placed in src
Create logs folder in project root
Logs written to logs/revpm.log

5️. Running the Application

Run App.java
Use console menu to:
Register
Login
forgot password
exit

6️. Unit Testing

Unit tests written for service layer
DAO layer mocked using Mockito
Tests can be run via JUnit in Eclipse


Author
Vayala Sai Kumar
