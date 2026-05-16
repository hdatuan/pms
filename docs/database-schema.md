# Cấu trúc Database (Database Schema)

Database: `pms` (MySQL 8+)

## Danh sách các Bảng và Trường dữ liệu quan trọng

### 1. Bảng `roles` (Quyền hạn)
Lưu trữ các nhóm quyền của người dùng.
- `id` (INT, PK, Auto Increment)
- `name` (VARCHAR, Unique): Tên quyền (ví dụ: ADMIN, MANAGER, STAFF).
- `description` (VARCHAR): Mô tả quyền.

### 2. Bảng `users` (Người dùng)
Lưu thông tin tài khoản đăng nhập.
- `id` (INT, PK, Auto Increment)
- `email` (VARCHAR, Unique): Tài khoản đăng nhập.
- `password` (VARCHAR): Mật khẩu.
- `fullname` (VARCHAR): Họ và tên.
- `role_id` (INT, FK): Liên kết tới bảng `roles`.

### 3. Bảng `jobs` (Dự án / Nhóm công việc)
Quản lý các dự án lớn.
- `id` (INT, PK, Auto Increment)
- `name` (VARCHAR, Unique): Tên dự án.
- `start_date` (DATE)
- `end_date` (DATE)

### 4. Bảng `status` (Trạng thái)
Danh mục các trạng thái của tác vụ.
- `id` (INT, PK, Auto Increment)
- `name` (VARCHAR, Unique): Tên trạng thái (Chưa thực hiện, Đang thực hiện, Đã thực hiện).

### 5. Bảng `tasks` (Tác vụ / Công việc)
Giao việc cho từng user.
- `id` (INT, PK, Auto Increment)
- `name` (VARCHAR): Tên công việc.
- `start_date` (DATE)
- `end_date` (DATE)
- `user_id` (INT, FK): Người được giao việc (liên kết bảng `users`).
- `job_id` (INT, FK): Thuộc dự án nào (liên kết bảng `jobs`).
- `status_id` (INT, FK): Trạng thái công việc (liên kết bảng `status`).

## Các Ràng buộc (Relationships & Constraints)
- **1-N (Role - User)**: 1 Quyền có nhiều User. (Foreign Key: `role_id` trong `users`).
- **1-N (User - Task)**: 1 User thực hiện nhiều Task. (Foreign Key: `user_id` trong `tasks`).
- **1-N (Job - Task)**: 1 Job bao gồm nhiều Task. (Foreign Key: `job_id` trong `tasks`).
- **1-N (Status - Task)**: 1 Trạng thái có nhiều Task. (Foreign Key: `status_id` trong `tasks`).
- Có ràng buộc Check `end_date >= start_date` ở cả bảng `jobs` và `tasks`.
