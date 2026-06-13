# Các tính năng cần hoàn thiện hoặc sửa đổi (TODO & Refactoring) - ĐÃ HOÀN THÀNH

Toàn bộ các module cốt lõi đã được nâng cấp, bổ sung đầy đủ các thao tác CRUD và tối ưu hóa chất lượng mã nguồn theo đúng kế hoạch.

### 1. Quản lý Dự án / Nhóm công việc (Job Management) - ✅ ĐÃ HOÀN THÀNH
- **Trạng thái**: Hoàn thiện toàn bộ các tính năng quản lý dự án.
- **Chi tiết**:
  - [x] Thêm tính năng Tạo mới dự án (Add Job) qua `/groupwork-add`.
  - [x] Thêm tính năng Chỉnh sửa dự án (Edit Job) qua `/groupwork-edit`.
  - [x] Thêm tính năng Xóa dự án (Delete Job - Cascade Tasks) qua `/groupwork-delete`.
  - [x] Thêm giao diện Xem chi tiết tiến độ dự án qua `/groupwork-details` (bao gồm biểu đồ hình tròn trực quan và danh sách công việc theo từng thành viên).

### 2. Quản lý Tác vụ (Task Management) - ✅ ĐÃ HOÀN THÀNH
- **Trạng thái**: Hoàn thiện toàn bộ các tính năng giao việc và theo dõi tiến độ.
- **Chi tiết**:
  - [x] Tạo form giao việc (Assign Task) chọn Job, chọn User thực hiện, đặt ngày bắt đầu/kết thúc qua `/task-add`.
  - [x] Cho phép Chỉnh sửa công việc (Edit Task) qua `/task-edit`.
  - [x] Cho phép Xóa/Hủy tác vụ (Delete Task) qua `/task-delete`.
  - [x] Cập nhật trạng thái công việc (Not Started, In Progress, Completed) từ phía nhân viên qua `/profile-edit` và admin/manager qua `/task-edit`.

### 3. Sửa đổi và Cải thiện Code (Code Quality & Refactoring) - ✅ ĐÃ HOÀN THÀNH
- **Quản lý người dùng**:
  - [x] Thêm đầy đủ tính năng Chỉnh sửa (`/user-edit`) và Xóa (`/user-delete`) phía admin.
- **Tối ưu hóa và Gom gọn Controller**:
  - [x] Tái cấu trúc (Refactor) gộp các servlet phân mảnh của từng module vào một Controller duy nhất (ví dụ: `RoleController`, `UserController`, `JobController`, `TaskController`, `ProfileController`) và phân nhánh xử lý theo HTTP Method (`doGet`/`doPost`) cùng `ServletPath`.
  - [x] Loại bỏ hoàn toàn các file Controller thừa thãi (`UserAddController`, `ProfileEditController`, `RoleAddController`, `RoleDeleteController`, v.v.).
- **Xác thực & Phân quyền**:
  - [x] Xây dựng lớp [AuthorizationFilter](file:///d:/WorkSpace/pms/src/main/java/hdatuan/filter/AuthorizationFilter.java) chặn request ở vòng ngoài, xử lý chuyển hướng về `/403` khi không đủ quyền.
  - [x] Loại bỏ hoàn toàn lỗ hổng bảo mật phân quyền ngang (IDOR) tại `/profile-edit` bằng việc kiểm tra quyền sở hữu tác vụ của user đang đăng nhập.
- **Xử lý ngoại lệ (Exception Handling)**:
  - [x] Chuẩn hóa trang lỗi `/403` hiển thị giao diện chuyên nghiệp thay vì để lộ stacktrace của hệ thống.
- **Hệ thống thông báo đồng bộ**:
  - [x] Sử dụng Modal Popup thông báo trung tâm trực quan (`showModal` / `showToast` qua `common.js`) cho tất cả các thao tác CRUD thành công hay thất bại trên toàn bộ hệ thống.
