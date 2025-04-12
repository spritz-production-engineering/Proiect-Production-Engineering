# 🏡 Airbnb-like Reservation App

## 📚 Project Overview

This is a web application inspired by Airbnb, designed to facilitate apartment rentals. In the app, a **property owner** can register multiple **apartments**, each belonging to a **location**. Users can **browse apartments** and **send reservation requests**, which must be **approved by the property owner** before being confirmed.

---

## 🏗️ Project Structure

### `config/`
- `AppConfig.java` – General app configuration

### `controller/`
- `ApartamentController.java` – Handles apartment-related requests
- `LocatieController.java` – Manages locations
- `ProprietarController.java` – Handles owner-related actions
- `RezervareController.java` – Manages reservations
- `UserController.java` – User-related functionality

### `data/`
- `ApartamentEntity.java` – Apartment data model
- `ApartamentRepository.java` – Repository for apartment database access
- `InformationEntity.java` – Stores additional metadata
- `UserEntity.java` – User model
- `UserRepository.java` – Repository for users
- `ProprietarEntity.java` – Property owner model
- `ProprietarRepository.java` – Repository for property owners
- `LocatieEntity.java` – Location model
- `LocatieRepository.java` – Repository for locations
- `RezervareEntity.java` – Reservation model
- `RezervareRepository.java` – Repository for reservations

---

## 🔄 Features

### 👤 For Users:
- View available apartments
- Send reservation requests
- Cancel pending requests
- View their reservation history

### 🏠 For Property Owners:
- Add new apartments
- Link apartments to locations
- View incoming reservation requests
- Accept or decline reservation requests

---

## 🧩 Entity Relationships

- One **Property Owner** can have multiple **Apartments**
- One **Location** contains multiple **Apartments**
- A **User** can send reservation requests for **Apartments**
- A **Reservation** has a status (pending, accepted, declined)

---

## 🚀 Tech Stack

- Java
- Spring Boot
- JPA / Hibernate
- Maven / Gradle
- RESTful API

---

## 📌 Future Improvements

- ID validation



## ✅ Performance test

- Integration tested with Testcontainers (MongoDB)
- Performance tested using Apache JMeter for the following endpoints:
  - `GET /api/user` – retrieve all users
  - `GET /api/user/{id}` – retrieve a specific user
  - `POST /api/user` – create a new user
  - `PUT /api/user/{id}` – update an existing user
  - `DELETE /api/user/{id}` – delete a user
- JMeter script: `performance/jmeter/user-performance-test.jmx`
- HTML report: `performance/jmeter/report/index.html`


![image](https://github.com/user-attachments/assets/835ab724-a521-4a96-9670-409963242168)






# Prerequisites

For using Github Codespaces, no prerequisites are mandatory.
Follow the [./PREREQUISITES.md](./PREREQUISITES.md) instructions to configure a local virtual machine with Ubuntu, Docker, IntelliJ.

# Access the code

* Fork the code GitHub repository under your Organization
  * https://github.com/UNIBUC-PROD-ENGINEERING/service
* Clone the code repository:
  * git@github.com:YOUR_ORG_NAME/service.git

# Run code in Github Codespaces

* Make sure that the Github repository is forked under your account / Organization
* Create a new Codespace from your forked repository
* Wait for the Codespace to be up and running
* Make sure that Docker service has been started
    * ```docker ps``` should return no error
* For running all services in docker:
    * Build the docker image of the hello world service
        * ```make build```
    * Start all the service containers
        * ```./start.sh```
* For running / debugging directly in Visual Studio Code
    * Start the MongoDB related services
        * ```./start_mongo_only.sh```
    * Start the Spring Boot service by clicking `Run` button inside Visual Studio Code
* Use [requests.http](requests.http) to test API endpoints

NOTE: for a live demo, please check out [this youtube video](https://youtu.be/-9ePlxz03kg)

# Run/debug code in IntelliJ
* Build the code
    * IntelliJ will build it automatically
    * If you want to build it from command line and also run unit tests, run: ```./gradlew build```
* Create an IntelliJ run configuration for a Jar application
    * Add in the configuration the JAR path to the build folder `./build/libs/hello-0.0.1-SNAPSHOT.jar`
* Start the MongoDB container using docker compose
    * ```docker-compose up -d mongo```
* Run/debug your IntelliJ run configuration
* Open in your browser:
    * http://localhost:8080/hello-world
    * http://localhost:8080/info

# Deploy and run the code locally as docker instance

* Build the docker image of the hello world service
    * ```make build```
* Start all the containers
    * ```./start.sh```

* Verify that all containers started, by running
  ```
  service git:(master) ✗  $ docker ps
  CONTAINER ID   IMAGE           COMMAND                  CREATED         STATUS         PORTS                      NAMES
  c1d05dddd3fe   mongo:5.0.2     "docker-entrypoint.s…"   6 seconds ago   Up 5 seconds   0.0.0.0:27017->27017/tcp   service_mongo_1
  e90bb406c139   hello-img       "java -jar /hello/li…"   6 seconds ago   Up 5 seconds   0.0.0.0:8080->8080/tcp     service_hello_1
  411475a7b596   mongo-express   "tini -- /docker-ent…"   6 seconds ago   Up 2 seconds   0.0.0.0:8090->8081/tcp     service_mongo-admin-ui_1
  ```
* Open in your browser:
    * http://localhost:8080/hello-world
    * http://localhost:8080/info
* You can test other API endpoints using [requests.http](requests.http)
* You can access the MongoDB Admin UI at:
  * http://localhost:8090 
