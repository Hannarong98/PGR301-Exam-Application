## PGR301-Exam-Application-Azure

[![Build Status](https://travis-ci.com/Hannarong98/PGR301-Exam-application.svg?token=DqMpxq41VWvgzW8Fy3oq&branch=feature/azure-deployment)](https://travis-ci.com/Hannarong98/PGR301-Exam-application)

Container registry pipeline setup
---
#### Encrypt following secrets
* `travis encrypt --pro ACR="YOUR_AZURE_CONTAINER_REGISTRY_URL"`
* `travis encrypt --pro AZURE_APP_ID="YOUR_AZURE_APP_OR_SERVICE_PRINCIPAL_ID"`
* `travis encrypt --pro AZURE_SP_PSW="YOUR_AZURE_SP_SECRET_OR_PASSWORD"`

Testing the application
--
#### 
* Start the application either through command-line or IDE
    * Set spring profile in `Application.kt` to `dev`
    * The application will then be accessible through `localhost:8080`

Metrics
---
* All measurements are done in controller classes with micrometer
* And these can be found under `controller` package


Available endpoints
---
##### Home - useful for statuscake uptime check
* `GET /` 

##### Courses
* `GET /api/courses`
* `GET /api/courses/:courseCode`
* `POST /api/courses`
     * `{"courseCode":"your_value", "courseName":"your_value"}`

##### Students
* `GET /api/students`
* `GET /api/students/:studentId`
* `GET /api/students/:studentId/exams`
* `POST /api/students/signup`
    * `{"studentId":"your_value"}`
* `PUT /api/students/:studentId/exams/:courseCode`

##### NOTE: Post requests accepts JSON as MediaType

Links
---
[Deployed here](https://pgr301exam.azurewebsites.net/)

[Infrastructure Azure](https://github.com/Hannarong98/pgr301-exam-azure-infrastructure)
