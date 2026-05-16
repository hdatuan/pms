# Tổng quan dự án (Project Context)

**Project Management System (PMS)** là một hệ thống quản lý dự án và công việc nội bộ dành cho doanh nghiệp, được thiết kế để thay thế các quy trình thủ công bằng một nền tảng tập trung.

### Kiến trúc và Công nghệ (Tech Stack)
- **Mô hình**: Standard MVC (Model-View-Controller)
- **Backend**: Java 8+, Servlet API, JSP
- **Database**: MySQL 8.0 với JDBC
- **Frontend**: Bootstrap 4, jQuery, Morris.js, DataTables
- **Build Tool**: Maven

### Mục tiêu dự án
Cung cấp một nền tảng Dashboard để:
- Theo dõi tiến độ công việc (Not Started, In Progress, Completed).
- Quản lý người dùng, phân quyền (RBAC - Role-Based Access Control).
- Quản lý dự án/nhóm công việc (Jobs) và các tác vụ (Tasks) chi tiết.
- Cung cấp trang cá nhân (Profile) cho từng nhân viên theo dõi việc được giao.
