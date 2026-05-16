# Các tính năng đã hoàn thiện (Implemented Features)

Dựa trên mã nguồn hiện tại trong thư mục `controller`, các tính năng sau đã được phát triển cơ bản và hoạt động:

1. **Xác thực và Phân quyền (Authentication & Authorization)**
   - Đăng nhập, Đăng xuất (`LoginController`, `LogoutController`).
   - Lọc phân quyền (Filter-based Auth) đảm bảo bảo mật các route.

2. **Quản lý Quyền (Role Management)**
   - Xem danh sách quyền (`RoleController`).
   - Thêm quyền mới (`RoleAddController`).
   - Xóa quyền (`RoleDeleteController`).

3. **Quản lý Người dùng (User Management)**
   - Xem danh sách người dùng (`UserController`).
   - Thêm người dùng mới với phân quyền cụ thể (`UserAddController`).

4. **Trang cá nhân (User Profile)**
   - Xem thông tin cá nhân và công việc được giao (`ProfileController`).
   - Chỉnh sửa thông tin cá nhân (`ProfileEditController`).

5. **Trang chủ / Dashboard (Home)**
   - Hiển thị tổng quan các số liệu (`HomeController`).
