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

1. **(Option 1)By Editing the application.properties[Not Recomanded]:**

    you can enter your mongodb directly inside the application.properties file
    by replacing the varibles.

2. **(Option 2)By Creating Envirnment Variables:**
    
    if you are using Intellij it provides an option to create envirnment variables for running th code.

**Variables in application.properties are defined as below:**

|Variable|Value|
|-|-|
|MONODB_USERNAME|Your MongoDB username|
|MONGODB_USERPASSWORD|Your MongoDB password|
|JWT_SECRECT_KEY|A 120 bit or longer sized Secret Key|

### Build The Project

Run the main `TeamTrack.java` file to start the project.


## EndPoints
|Method|Endpoint|Description|
|-|-|-|
|POST|`api/auth`|Authentication|
|POST|`api/auth/sign-in`|Create a New User|
|GET|`/health-check`|check if app is working|
|PUT|`users/update`|Update user credentials|
|GET|`admin/all-users`|Retrive all users who are admin