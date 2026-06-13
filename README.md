<div align="center">

# Project Management System
### A Modern Internal Work & Project Management Platform

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com/)
[![Servlet](https://img.shields.io/badge/Servlet-Using%20v3.1-orange?style=for-the-badge)](https://jakarta.ee/specifications/servlet/)
[![Bootstrap](https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white)](https://getbootstrap.com/)
[![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)


</div>

---

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [System Architecture](#system-architecture)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [Authors](#authors)

---

## Overview
**Project Management System** is a robust, enterprise-grade internal management tool designed to streamline team operations. It provides a centralized dashboard for tracking jobs, assigning tasks, and monitoring real-time progress across departments.


This application replaces scattered spreadsheets with a unified, role-based system that improves data integrity and operational transparency.

---

## Features
### Core Modules
*   **Dynamic Dashboard**: Visual analytics showing task distribution (Not Started vs In Progress vs Completed).
*   **User Management**: Full CRUD operations for system users with role assignment.
*   **Role-Based Access Control (RBAC)**: secure permission system (e.g., Admin, Manager, Staff).
*   **Job Management**: Create, edit, and archive projects with timeline tracking.
*   **Task Management**: Granular task assignment linked to specific Jobs and Users.
*   **User Profile**: Personal dashboard for users to update credentials and view assigned work.

---

## System Architecture
The project strictly follows the **Standard MVC (Model-View-Controller)** pattern to ensure scalability and maintainability.

| Layer | Responsibility | Components |
|-------|---------------|------------|
| **View** | User Interface | JSP, Bootstrap, JSTL |
| **Controller** | Request Handling | Java Servlets, Filters |
| **Service** | Business Logic | Java Classes (Service Layer) |
| **Repository** | Data Access | JDBC, SQL Queries |
| **Model** | Data Transfer | POJO Entities |

---

## Tech Stack
### Backend
*   **Language**: Java 8+
*   **Core**: Java Servlet API, JSP
*   **Database**: MySQL 8.0
*   **Security**: Filter-based Authentication & Session Management

### Frontend
*   **Framework**: Bootstrap 4
*   **Scripting**: jQuery, JavaScript
*   **Visualization**: Morris.js (Charts), Toast (Notifications), DataTables

---

## Getting Started (Local Setup)

The easiest and fastest way to build and run this application is using **Docker Compose**. It packages Apache Tomcat 9 and MySQL 8.0, allowing you to run the entire system with a single command without installing Java, Maven, or Tomcat locally on your host machine.

### Prerequisites
- For **Windows** & **macOS**: Install and start [Docker Desktop](https://www.docker.com/products/docker-desktop/).
- For **Linux**: Install and start [Docker Engine](https://docs.docker.com/engine/install/) and the [Docker Compose CLI plugin](https://docs.docker.com/compose/install/).

### Step-by-Step Installation

1. **Clone the Repository**
   ```bash
   git clone https://github.com/hdatuan/CRM_Application.git
   cd CRM_Application
   ```

2. **Configure Environment Variables (`.env`)**
   - Create a `.env` file by copying the template file `.env.example`:
     ```bash
     cp .env.example .env
     ```
   - Open the `.env` file and customize the database name (`MYSQL_DATABASE`), root password (`MYSQL_ROOT_PASSWORD`), external port mapping (`MYSQL_PORT`), and application database credentials (`DB_USER`, `DB_PASS`) to fit your needs.

3. **Start the Application**
   Run the following command in your terminal from the project root:
   ```bash
   docker compose up --build -d
   ```
   *This command will:*
   - Compile the Java source code into a WAR file inside a multi-stage builder container.
   - Boot up the MySQL database container and automatically import the sample schema and data from `database/pms.sql`.
   - Start the Apache Tomcat 9 container and deploy the application as the root context (`ROOT.war`).

4. **Access the Application**
   Open your browser and navigate to: **[http://localhost:8080/](http://localhost:8080/)**
   *(Note: Since the app is deployed in the root context, no `/pms` path suffix is required).*

5. **Stop the Application**
   To stop and remove containers while preserving the database volume, run:
   ```bash
   docker compose down
   ```
   To remove containers and completely delete the database volumes, run:
   ```bash
   docker compose down -v
   ```

---

### Default Test Credentials
- **Admin**: `admin@gmail.com` / `123456`
- **Manager**: `manager01@gmail.com` / `123456`
- **Staff**: `staff.dev01@gmail.com` / `123456`

---

## Project Structure

```bash
pms_app/  # Runtime artifactId/context-path
├── 📂 database   # included sample database 
│
├── 📂 src/main/java/hdatuan   # Core Backend Logic
│   ├── 📂 config              # DB Connections (MySQLConfig)
│   ├── 📂 controller          # Servlets (Login, Task, User...)
│   ├── 📂 entity              # POJOs (User, Role, Job, Task)
│   ├── 📂 filter              # Auth Filters
│   ├── 📂 repository          # JDBC Data Access
│   └── 📂 service             # Business Logic Layer
│
├── 📂 src/main/resources      # Configuration files
│   ├── db.properties          # Configure your database
│   └── db.properties.example  # Example of db.properties
│
├── 📂 src/main/webapp         # Frontend Assets
│   ├── 📂 bootstrap           # CSS Framework
│   ├── 📂 css / js            # Custom Styles & Scripts
│   ├── 📂 plugins             # 3rd Party Libs (Charts, Tables)
│   └── 📂 WEB-INF             # Protected Configuration
│       ├── 📂 views           # JSP View Templates (Protected)
│       └── 📂 lib             # JAR Dependencies
│
└── 📄 README.md               # Project Documentation
```


---

## Contributing
Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## Authors
**hdatuan** - *Backend Architecture & Development*

