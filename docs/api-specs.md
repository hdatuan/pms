# Tài liệu API & Endpoints

Dự án này sử dụng mô hình Server-Side Rendering với JSP, do đó các Endpoints chủ yếu trả về giao diện HTML (JSP forward) hoặc xử lý form submit.

## Danh sách các Endpoints chính

### 1. Xác thực (Authentication) - `LoginController`, `LogoutController`
- **`GET /login`**: Hiển thị trang đăng nhập.
- **`POST /login`**: Xử lý đăng nhập. Body (Form): `email`, `password`.
- **`GET /logout`**: Xóa session và đăng xuất.

### 2. Trang chủ & Cá nhân - `HomeController`, `ProfileController`
- **`GET /home`**: Trang Dashboard tổng quan (hiển thị thống kê tác vụ).
- **`GET /profile`**: Xem thông tin cá nhân và danh sách tác vụ được giao cho người dùng đang đăng nhập.
- **`GET /profile-edit`**: Form cập nhật trạng thái/tiến độ tác vụ cá nhân. Query: `id` (ID của Task). *(Kiểm tra bảo mật IDOR)*.
- **`POST /profile-edit`**: Cập nhật trạng thái/tiến độ tác vụ cá nhân. Body (Form): `id`, `startDate`, `endDate`, `statusId`. *(Kiểm tra bảo mật IDOR)*.

### 3. Quản lý Quyền (Role) - `RoleController` *(Chỉ dành cho Admin)*
- **`GET /role`**: Hiển thị danh sách các vai trò (roles).
- **`GET /role-add`**: Hiển thị form thêm vai trò.
- **`POST /role-add`**: Lưu vai trò mới. Body (Form): `name`, `description`.
- **`GET /role-edit`**: Hiển thị form cập nhật vai trò. Query: `id`.
- **`POST /role-edit`**: Cập nhật thông tin vai trò. Body (Form): `id`, `name`, `description`.
- **`GET /role-delete`**: Xóa vai trò. Query: `id`.

### 4. Quản lý Người dùng (User) - `UserController` *(Chỉ dành cho Admin)*
- **`GET /user`**: Danh sách người dùng. *(Tất cả người dùng đã đăng nhập đều xem được)*.
- **`GET /user-add`**: Hiển thị form thêm người dùng mới.
- **`POST /user-add`**: Thêm người dùng mới. Body (Form): `email`, `password`, `fullname`, `role_id`.
- **`GET /user-edit`**: Hiển thị form cập nhật người dùng. Query: `id`.
- **`POST /user-edit`**: Cập nhật thông tin người dùng. Body (Form): `id`, `email`, `fullname`, `role_id`, `password` (không bắt buộc).
- **`GET /user-delete`**: Xóa người dùng. Query: `id`.

### 5. Quản lý Dự án / Nhóm công việc (Job) - `JobController`
- **`GET /groupwork`**: Hiển thị danh sách các Dự án (Jobs). *(Tất cả các vai trò)*.
- **`GET /groupwork-add`**: Hiển thị form thêm dự án mới. *(Admin & Manager)*.
- **`POST /groupwork-add`**: Thêm dự án mới. Body (Form): `name`, `startDate`, `endDate`. *(Admin & Manager)*.
- **`GET /groupwork-edit`**: Hiển thị form cập nhật dự án. Query: `id`. *(Admin & Manager)*.
- **`POST /groupwork-edit`**: Cập nhật dự án. Body (Form): `id`, `name`, `startDate`, `endDate`. *(Admin & Manager)*.
- **`GET /groupwork-delete`**: Xóa dự án (tự động xóa tác vụ liên quan). Query: `id`. *(Admin & Manager)*.
- **`GET /groupwork-details`**: Xem chi tiết tiến độ dự án (biểu đồ thống kê và tiến trình của từng thành viên). Query: `id`. *(Admin & Manager)*.

### 6. Quản lý Tác vụ (Task) - `TaskController`
- **`GET /task`**: Hiển thị danh sách các tác vụ. *(Tất cả các vai trò)*.
- **`GET /task-add`**: Hiển thị form thêm tác vụ mới (giao việc). *(Admin & Manager)*.
- **`POST /task-add`**: Thêm tác vụ mới. Body (Form): `name`, `job_id`, `user_id`, `startDate`, `endDate`, `status_id`. *(Admin & Manager)*.
- **`GET /task-edit`**: Hiển thị form chỉnh sửa tác vụ. Query: `id`. *(Admin & Manager)*.
- **`POST /task-edit`**: Cập nhật tác vụ. Body (Form): `id`, `name`, `job_id`, `user_id`, `startDate`, `endDate`, `status_id`. *(Admin & Manager)*.
- **`GET /task-delete`**: Xóa tác vụ. Query: `id`. *(Admin & Manager)*.
