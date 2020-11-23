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
* All measurements are done in controller classes with micrometer
* And these can be found under `controller` package
* How see them
    * Start influxdb then navigate to `localhost:8083`
    * Change database to `mydb`
    * Play around with endpoints
    * Do `SHOW MEASUREMENTS` and see if measurements are there
    * Query for example `SELECT * FROM long_running_task` or anything that were used


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

Deployment
---
* Once you have configured everything you will only need to change
    * `image` in container block of `main.tf` for each new revision

Links
---

[Deployed here](https://cloud-run-srv-296202-goizh23afq-uc.a.run.app/)

[Infrastructure](https://github.com/Hannarong98/PGR301-Exam-Infrastructure)
