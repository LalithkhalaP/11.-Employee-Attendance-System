# 11.-Employee-Attendance-System
Objective:
To implement an employee registration and attendance login system using Servlet lifecycle methods + JDBC.
Description:
Employee registers using register.html.
Employee logs in using login.html.
Servlet verifies users from database using JDBC in the service() method.
The database connection is created in init() and closed in destroy().
Requirements:
Frontend:
register.html → Employee registration
login.html → Employee login
Servlet:
init() → load JDBC driver, get DB connection
service() →
Insert employee during registration
Verify employee during login
destroy() → close connection
Database:
Employee table with id, name, username, password
connect jar and server
