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

## Getting Started

### Prerequisites
*   JDK: [Java Development Kit (JDK 8+)](https://www.oracle.com/java/technologies/downloads/)
*   Web Server: [Apache Tomcat 9.0](https://tomcat.apache.org/download-90.cgi)
*   Database: [MySQL Server 8.0+](https://dev.mysql.com/downloads/installer/) (or run as a container on Docker)
*   IDE: [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) (Ultimate Edition is recommended or Community Edition with Smart Tomcat plugin)

### Installation
1.  **Clone the repository**
    ```sh
    git clone https://github.com/hdatuan/pms.git
    cd pms
    ```

2.  **Database Setup**
    *   Open MySQL Workbench or CLI.
    *   Execute the script located at `/database/schema.sql` (if available) or create a database named `pms`.
    *   Update database credentials in `src/main/resources/db.properties`:
        ```properties
        db.url=jdbc:mysql://localhost:3306/pms
        db.user=root
        db.password=your_password
        ```
    * Since you are working with Docker, you can quickly spin up the database environment without local installation using the following command:
         ```bash
      docker run --name pms-mysql -e MYSQL_ROOT_PASSWORD=your_password -e MYSQL_DATABASE=pms -p 3306:3306 -d mysql:8.0
         ```
       
3.  **IDE Setup (IntelliJ IDEA)**
    *   `File` -> `Open` and navigate to the project root directory.
    *   `File` -> `Project Structure`.
        * Under **Modules**, ensure `src/main/java` is marked as **Sources** and `src/main/webapp` is recognized as a **Web Resource Directory**.
        * Under **Libraries**, click the **+** icon and add all `.jar` files from `src/main/webapp/WEB-INF/lib` to resolve dependencies.
    * **Tomcat Server Setup**:
        * Go to `Run` -> `Edit Configurations...`.
        * Click the **+** icon, select **Tomcat Server** -> **Local**.
        * In the **Server** tab, configure the path to your Tomcat 9.0 installation.
        * In the **Deployment** tab, click **+** -> **Artifact** and select `pms:war exploded`.
        * Set the **Application context** to `/pms`.

4.  **Access the App**
    * Click the **Run** icon in IntelliJ.  
    * Open Browser: `http://localhost:8080/pms`
    * **Default Credentials**:
        * **Admin**: `admin@gmail.com` / `123456`
        * **Manager**: `manager01@gmail.com` / `123456`
        * **Staff**: `staff.dev01@gmail.com` / `123456`

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

