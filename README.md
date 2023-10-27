# Nested Comment Service

This service is aimed at providing the structure for having infinite nested comments. 
This service expose multiple REST End points for this functionality. 

Service Tech Stack 

- Spring Boot Framework (maven)
- Mongo DB 
- Redis for caching api response
- Swagger UI 

How to run this APP 

- Clone the repository (https://github.com/yuyuTsu00/CommentService.git)
- Let maven download all the required dependency 
- setup mongo and redis in local
- update mongo db and redis db configs in application.properties file (src/main/resources/application.properties)
- Run App, main class (src/main/java/com/intuit/interview/commentservice/CommentServiceApplication.java)
- Swagger URL : http://localhost:8080/swagger-ui/index.html#/