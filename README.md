<div align="center">

# Project Management System
### A Modern Internal Work & Project Management Platform

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com/)
[![Servlet](https://img.shields.io/badge/Servlet-v3.1-orange?style=for-the-badge)](https://jakarta.ee/specifications/servlet/)
[![Bootstrap](https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white)](https://getbootstrap.com/)
[![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)
[![Apache Tomcat](https://img.shields.io/badge/Tomcat-F8DC75?style=for-the-badge&logo=apache-tomcat&logoColor=black)](https://tomcat.apache.org/)
[![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)](https://maven.apache.org/)


</div>

---

## Table of Contents
- [Overview](#overview)
- [Documentation](#documentation)
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

## Documentation
Additional technical documentation can be found in the [docs/](/docs) directory:

- **[Project Overview](/docs/overview.md)**: Details the goals, features, and tech stack of the project.
- **[System Architecture](/docs/architecture.md)**: Details the MVC request/response pipeline and class organization.
- **[Database Schema](/docs/database-schema.md)**: Tables, attributes, types, constraints, and relationships.
- **[API & Routes](/docs/api.md)**: Endpoint reference map detailing parameters, methods, and access permissions.
- **[Environment Variables](/docs/env.md)**: Variable definitions for configuring database connections and Docker containers.
- **[Roles & Permissions](/docs/roles-permissions.md)**: Details the access control matrix and developer guides.

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
   git clone https://github.com/hdatuan/pms.git
   cd pms
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
pms/
├── 📂 database          # Seed SQL scripts for MySQL initialization
├── 📂 docs              # Technical project documentation
├── 📂 src/main/java/hdatuan   # Backend MVC source code
│   ├── 📂 config        # Database connectivity config
│   ├── 📂 controller    # HTTP Servlets handling routes
│   ├── 📂 entity        # Data Transfer Models (POJOs)
│   ├── 📂 filter        # Authentication & Authorization filters
│   ├── 📂 repository    # JDBC CRUD Query classes
│   └── 📂 service       # Core Business Logic layer
├── 📂 src/main/resources # Application properties and resources
├── 📂 src/main/webapp   # Frontend web templates & static resources
│   ├── 📂 bootstrap     # Style framework files
│   ├── 📂 css / js      # Layout styling & micro-animations logic
│   ├── 📂 plugins       # Third-party visualization plugins (Morris.js)
│   └── 📂 WEB-INF/views # JSP HTML layouts
├── 📄 Dockerfile        # Multi-stage image packaging instructions
├── 📄 docker-compose.yml # Service container orchestrations
└── 📄 README.md         # Landing page and setup manual
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

