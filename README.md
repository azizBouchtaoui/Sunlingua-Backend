# SUNLINGUA Backend

This repository contains the backend for the SUNLINGUA project. The backend is built with Spring Boot and uses PostgreSQL as the database. Docker is used to containerize both the application and the database for easy setup.

### Prerequisites

Make sure you have the following installed on your machine:

- Docker (https://docs.docker.com/get-docker/)
- Docker Compose (https://docs.docker.com/compose/install/)

### Getting Started

#### 1. Clone the Repository

First, clone this repository to your local machine:

```
git clone https://github.com/azizBouchtaoui/Sunlingua-Backend.git  
cd Sunlingua-Backend
```

#### 2. Build and Run the Containers

To run the Spring Boot backend, PostgreSQL, and pgAdmin, use Docker Compose:
```
docker-compose up --build
```

This will:

- Build and start the Spring Boot application.
- Set up a PostgreSQL database.
- Start pgAdmin for database management.

#### 3. Access the Application

- **Backend API**: The backend API will be available at:  
  http://localhost:8080

- **pgAdmin**: The pgAdmin interface can be accessed at:  
  http://localhost:5050

  **Login Credentials**:  
  Email: admin@admin.com  
  Password: admin


  After logging in, add the PostgreSQL server with the following connection details:

    - Host: db
    - Port: 5432
    - Username: postgres
    - Password: root
    - Database: sunlingua

#### 4. Database Details

- **Database Host**: localhost
- **Database Port**: 5432
- **Database Name**: sunlingua
- **Username**: postgres
- **Password**: root

#### 5. Test API Endpoints

You can use Postman, curl, or a browser to interact with the API. Example endpoints include:

##### Register a User

To register a new user:

- **Endpoint**: POST /api/v1/auth/register

**Request body example**:
```
{
    "firstname": "aziz",
    "lastname": "bouchtaouoi",
    "email": "aziz@example.com",
    "password": "password123",
    "languesParlees": "French, English",
    "niveauCompetence": "Intermediate",
    "objectifsApprentissage": "Improve speaking skills",
    "preferencesRencontre": "Weekends",
    "presentation": "I'm passionate about languages.",
        "role": "USER"

}
```

###### Authenticate a User (Login)

To authenticate a registered user and receive access and refresh tokens:

- **Endpoint**: POST /api/v1/auth/login

**Request body example**:
```
{
"email": "aziz@example.com",  
"password": "password123"
}
```

The response will contain the following:
```
{
"access_token": "your-access-token",
"refresh_token": "your-refresh-token"
}

```


#### Add Learning Resources (with Authentication and Internationalization)
Before you can retrieve resources, you must add them:

- **Endpoint**: POST /api/v1/resources

- **Authorization**: Add the access_token to the header:

`Authorization: Bearer your-access-token`

Request body example:
```
{
    "resourceTitle": "Basic Frenchd Lesson",
    "resourceType": "Article",
    "resourceLink": "http://example.com/french-lesson"
}
```
- To get the response in a different language, add the lang parameter ***(e.g., lang=fr for French)***:
  This will return the list of available learning resources.

Example request:
- **Endpoint**: GET /api/v1/resources?lang=fr

This will return the list of available learning resources in French or the selected language.



#### Access Protected Resources (with Authentication )

Once authenticated, you can use the access token to access protected resources. If you want to test internationalization, add the lang parameter to get responses in another language.

**Example of accessing protected resources (Learning Resources)**:

- **Endpoint**: GET /api/v1/resources
- **Authorization**: Add the access_token to the header like this:

  `Authorization: Bearer your-access-token`

#### 6. Stopping the Application

To stop the containers, run:
```
docker-compose down
```

#### 7. Logs

Application logs will be saved in the `logs/` directory, which is mapped to the host system:

**Log file location**: logs/app.log

### Troubleshooting

#### Port Conflicts

If you encounter port conflicts (e.g., if PostgreSQL is already running on port 5432 locally), you can modify the ports in the `docker-compose.yml` file.

#### Database Access Issues

Ensure that pgAdmin is configured with the correct credentials:

- Host: db
- Port: 5432
- Username: postgres
- Password: root

#### Rebuilding the Containers

If you need to rebuild the containers, use:
```
docker-compose up --build
```

### Contact

If you encounter any issues, please contact the backend team at [m.bouchtaouiaziz@gmail.com].
