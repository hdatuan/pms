# Project Overview

The **Project Management System (PMS)** is a web-based internal tool designed for businesses to streamline project planning, task delegation, and employee progress tracking. It provides a centralized dashboard to replace scattered spreadsheets and manual processes with a structured, role-based workflow.

## Core Modules & Features

1. **Dynamic Dashboard**
   - High-level project health monitoring.
   - Real-time task status analytics (Not Started, In Progress, Completed) visualized via interactive charts.

2. **Role-Based Access Control (RBAC)**
   - Strict security policies separating actions for Administrators, Managers, and Staff members.
   - Secure URL endpoints protected by intercepting Filters.

3. **User Management**
   - CRUD (Create, Read, Update, Delete) operations for corporate accounts.
   - Automated duplicate email prevention and secure password updating workflows.

4. **Job (Project) Management**
   - High-level timeline configuration (Start & End dates).
   - Interactive progress checking with member task completion breakdown.
   - Automatic cascade deletion of all associated tasks upon project removal.

5. **Task Management**
   - Granular task assignment to specific employees within a project.
   - Status indicators and feedback loops for progress tracking.

6. **Employee Profile & Work Space**
   - Personal dashboard for staff to view tasks assigned to them.
   - Self-service task updates (adjust start/end dates and status) protected against IDOR (Insecure Direct Object Reference) vulnerabilities.

## Technology Stack

- **Backend:** Java 8+, Java Servlet API 3.1, JDBC.
- **Frontend:** Bootstrap 4, jQuery, Morris.js (Charts), DataTables, JSP (JavaServer Pages) & JSTL.
- **Database:** MySQL 8.0.
- **Deployment:** Docker & Docker Compose.
