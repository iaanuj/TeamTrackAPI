# TeamTrack

TeamTrack is a collaborative to-do list API built with Java Spring Boot and MongoDB. It allows users to create, manage, and share their tasks with team members, enhancing productivity and collaboration.

## Features

- **User Management**: Create and manage user accounts.
- **Collaborative Tasks**: Share tasks with team members and collaborate in real-time.
- **Task Management**: Create, update, delete, and list tasks.
- **Categorization**: Create Groups where people can come together to work a Project.
- **Priority Levels**: Assign priority levels to tasks.
- **Due Dates**: Set and manage due dates for tasks.

## Technologies Used

- **Java**: Programming language used to build the application.
- **Spring Boot**: Framework for building RESTful APIs.
- **MongoDB**: NoSQL database for storing user and task data.
- **Maven**: Dependency management and build tool.

## Prerequisites

- Java 11 or higher
- MongoDB installed and running
- Maven installed

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/iaanuj/TeamTrackAPI.git
cd TeamTrackAPI
```

### Configure MongoDB

1. **(Option 1)By Editing the application.yml[Not Recommended]:**

    you can enter your mongodb URI directly inside the application.yml file
    by replacing the variable.

2. **(Option 2)By Creating Environment Variables:**
    
    if you are using Intellij it provides an option to create environment variables for running the code.

**Variables in application.properties are defined as below:**

| Variable        | Value                                                   |
|-----------------|---------------------------------------------------------|
| MONODB_URI      | Your MongoDB URI link with username and password        |
| JWT_SECRECT_KEY | At least 256-bits Secret Key for HMAC-SHA               |
| FRONTEND_URL    | Frontend URL to connect with backend using @CrossOrigin |
| MAIL_USERNAME   | Your mail username                                      |
| MAIL_PASSWORD   | Your generated app-specific password.                   |



### Build The Project

Run the main `TeamTrack.java` file to start the project.


## EndPoints
| Method | Endpoint                   | Description                                    |
|--------|----------------------------|------------------------------------------------|
| POST   | `auth/sign-in`             | Create a New User and sends confirmation code. |
| POST   | `auth/log-in`              | log in via jwt                                 |
| POST   | `auth/verify-confirmation` | for verifying user                             |
| GET    | `health-check`             | check if app is working                        |
| PUT    | `user/update-username`     | Update user name                               |
| GET    | `admin/all-users`          | For admins to retrieve all users               |