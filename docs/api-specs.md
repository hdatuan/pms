# Tài liệu API & Endpoints

Dự án này sử dụng mô hình Server-Side Rendering với JSP, do đó các Endpoints chủ yếu trả về giao diện HTML (JSP forward) hoặc xử lý form submit, ít sử dụng RESTful API trả về JSON.

## Danh sách các Endpoints chính

### 1. Xác thực (Authentication)
- **`GET /login`**: Hiển thị trang đăng nhập.
- **`POST /login`**: Xử lý đăng nhập. Body: `email`, `password`.
- **`GET /logout`**: Xóa session và đăng xuất.

### 2. Trang chủ & Cá nhân
- **`GET /home`**: Trang Dashboard tổng quan.
- **`GET /profile`**: Xem thông tin cá nhân và tác vụ được giao.
- **`POST /profile-edit`**: Cập nhật thông tin cá nhân. Body (Form): các thông tin cá nhân tùy chỉnh.

### 3. Quản lý Quyền (Role)
- **`GET /role`**: Hiển thị danh sách các quyền.
- **`GET /role-add`**: Form thêm quyền.
- **`POST /role-add`**: Lưu quyền mới. Body: `name`, `description`.
- **`GET /role-edit`**: Form cập nhật quyền. Query: `id`.
- **`POST /role-edit`**: Cập nhật quyền. Body: `id`, `name`, `description`.
- **`GET /role-delete`**: Xóa quyền. Query: `id`.

### 4. Quản lý Người dùng (User)
- **`GET /user`**: Danh sách người dùng.
- **`GET /user-add`**: Form thêm/sửa người dùng.
- **`POST /user-add`**: Thêm người dùng mới. Body: `email`, `password`, `fullname`, `role_id`.
- **`POST /user-edit`**: Cập nhật người dùng.
- **`GET /user-delete`**: Xóa người dùng. Query: `id`.

### 5. Quản lý Dự án (Job)
- **`GET /groupwork`**: Hiển thị danh sách các Dự án (Jobs).

### 6. Quản lý Tác vụ (Task)
- **`GET /task`**: Hiển thị danh sách các Tác vụ.
