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


