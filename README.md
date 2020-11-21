## PGR301-Exam-Application

[![Build Status](https://travis-ci.com/Hannarong98/PGR301-Exam-application.svg?token=DqMpxq41VWvgzW8Fy3oq&branch=master)](https://travis-ci.com/Hannarong98/PGR301-Exam-application)

Container registry pipeline setup
---
#### Encrypt following secrets
* `travis encrypt-file --pro google-key.json --add`
    * `google-key.json.enc` file is safe to commit to the repository
    * `google-key.json` contains google service account private key __DO NOT__ commit this file

Testing the application
--
#### 
* Start the application either through command-line or IDE
    * Set spring profile in `Application.kt` to `dev`
    * The application will then be accessible through `localhost:8080`

Metrics
---
* All measurements are done in controller classes
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