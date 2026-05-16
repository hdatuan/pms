# Các tính năng cần hoàn thiện hoặc sửa đổi (TODO & Refactoring)

Một số module cốt lõi hiện tại mới chỉ ở mức sơ khai, thiếu các thao tác CRUD (Thêm, Sửa, Xóa) cơ bản hoặc cần cải thiện chất lượng code:

### 1. Quản lý Dự án / Nhóm công việc (Job Management)
- **Hiện trạng**: `JobController` mới chỉ có phương thức `doGet` để hiển thị danh sách (`/groupwork`).
- **Cần làm**:
  - Thêm tính năng Tạo mới dự án (Add Job).
  - Thêm tính năng Chỉnh sửa dự án (Edit Job).
  - Thêm tính năng Xóa dự án (Delete Job).

### 2. Quản lý Tác vụ (Task Management)
- **Hiện trạng**: `TaskController` mới chỉ có phương thức `doGet` để hiển thị danh sách tác vụ (`/task`).
- **Cần làm**:
  - Cần form để giao việc (Assign Task): chọn Job, chọn User thực hiện, đặt ngày bắt đầu/kết thúc.
  - Cập nhật trạng thái công việc (Not Started, In Progress, Completed).
  - Xóa/Hủy tác vụ.

### 3. Sửa đổi và Cải thiện Code (Code Quality & Refactoring)
- **Quản lý người dùng**: Còn thiếu tính năng chỉnh sửa và xóa User (Edit/Delete User) ở phía quản trị viên.
- **Tối ưu hóa Controller**: Các Controller đang bị chia nhỏ ra thành quá nhiều file (ví dụ `RoleController`, `RoleAddController`, `RoleDeleteController`). Nên gom lại (Refactor) thành một `RoleServlet` duy nhất và dùng tham số URL hoặc phương thức HTTP phù hợp để quản lý.
- **Xử lý ngoại lệ (Exception Handling)**: Cần chuẩn hóa việc hiển thị trang lỗi 404, 500 thay vì để lộ stacktrace.
