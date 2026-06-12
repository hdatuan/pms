# Các tính năng đã hoàn thiện & Đánh giá chất lượng mã nguồn (Implemented Features & Code Review)

Tài liệu này đối chiếu và xác nhận trạng thái thực tế của các tính năng dựa trên việc rà soát toàn bộ mã nguồn của dự án (Controller, Repository, Service, View), đồng thời chỉ ra các điểm chưa hợp lý trong code.

---

## I. Xác nhận Trạng thái các Tính năng (Feature Verification)

### 1. Xác thực và Phân quyền (Authentication & Authorization)
* **Đăng nhập / Đăng xuất**: Hoàn thiện qua [LoginController](file:///d:/WorkSpace/pms/src/main/java/hdatuan/controller/LoginController.java) và [LogoutController](file:///d:/WorkSpace/pms/src/main/java/hdatuan/controller/LogoutController.java).
* **Bộ lọc bảo mật (Filters)**:
  * [AuthenticationFilter](file:///d:/WorkSpace/pms/src/main/java/hdatuan/filter/AuthenticationFilter.java): Kiểm tra trạng thái đăng nhập, cho phép truy cập tài nguyên tĩnh và trang login, chuyển hướng các request chưa đăng nhập về `/login`.
  * [AuthorizationFilter](file:///d:/WorkSpace/pms/src/main/java/hdatuan/filter/AuthorizationFilter.java): Phân quyền chi tiết dựa trên vai trò (Role):
    * **Chỉ Admin**: `/user-add`, `/user-edit`, `/user-delete`, `/role-add`, `/role-edit`, `/role-delete`.
    * **Admin và Manager**: `/task-add`, `/task-edit`, `/groupwork-add`, `/groupwork-edit`.

### 2. Quản lý Quyền (Role Management) - *Đầy đủ CRUD*
* **Xem danh sách**: Hoàn thiện qua [RoleController](file:///d:/WorkSpace/pms/src/main/java/hdatuan/controller/RoleController.java) (`/role`).
* **Thêm quyền mới**: Hoàn thiện qua [RoleAddController](file:///d:/WorkSpace/pms/src/main/java/hdatuan/controller/RoleAddController.java) (`/role-add`).
* **Chỉnh sửa quyền**: Hoàn thiện qua [RoleAddController](file:///d:/WorkSpace/pms/src/main/java/hdatuan/controller/RoleAddController.java) (`/role-edit`). *(Trước đây tài liệu cũ bỏ sót tính năng này).*
* **Xóa quyền**: Hoàn thiện qua [RoleDeleteController](file:///d:/WorkSpace/pms/src/main/java/hdatuan/controller/RoleDeleteController.java) (`/role-delete`). *(Lưu ý: Có bug logic redirect ở Controller khiến thông báo không hiển thị).*

### 3. Quản lý Người dùng (User Management) - *Đầy đủ CRUD*
* **Xem danh sách**: Hoàn thiện qua [UserController](file:///d:/WorkSpace/pms/src/main/java/hdatuan/controller/UserController.java) (`/user`).
* **Thêm người dùng**: Hoàn thiện qua [UserAddController](file:///d:/WorkSpace/pms/src/main/java/hdatuan/controller/UserAddController.java) (`/user-add`).
* **Chỉnh sửa thông tin**: Hoàn thiện qua [UserAddController](file:///d:/WorkSpace/pms/src/main/java/hdatuan/controller/UserAddController.java) (`/user-edit`). *(Trước đây tài liệu cũ bỏ sót tính năng này).*
* **Xóa người dùng**: Hoàn thiện qua [UserController](file:///d:/WorkSpace/pms/src/main/java/hdatuan/controller/UserController.java) (`/user-delete`). *(Trước đây tài liệu cũ bỏ sót tính năng này).*

### 4. Quản lý Dự án / Nhóm công việc (Job Management) - *Chưa hoàn thiện*
* **Hiện trạng**: Chỉ mới hỗ trợ xem danh sách dự án qua [JobController](file:///d:/WorkSpace/pms/src/main/java/hdatuan/controller/JobController.java) (`/groupwork`).
* **Thiếu sót**: Chưa có Servlet/Controller xử lý thêm/sửa/xóa dự án, mặc dù file giao diện [groupwork-add.jsp](file:///d:/WorkSpace/pms/src/main/webapp/WEB-INF/views/groupwork-add.jsp) đã được tạo sẵn và liên kết "Thêm mới" trên giao diện trỏ tới `/groupwork-add`.

### 5. Quản lý Tác vụ (Task Management) - *Chưa hoàn thiện*
* **Hiện trạng**: Chỉ mới hỗ trợ xem danh sách tác vụ tổng quan qua [TaskController](file:///d:/WorkSpace/pms/src/main/java/hdatuan/controller/TaskController.java) (`/task`).
* **Thiếu sót**: Chưa có Servlet/Controller cho việc tạo mới (giao việc), chỉnh sửa hay xóa tác vụ ở góc độ Admin/Manager, mặc dù giao diện [task.jsp](file:///d:/WorkSpace/pms/src/main/webapp/WEB-INF/views/task.jsp) có nút thêm mới trỏ tới file tĩnh `task-add.html`.

### 6. Trang cá nhân & Tiến độ (User Profile & Task Update)
* **Xem thông tin cá nhân**: Hoàn thiện qua [ProfileController](file:///d:/WorkSpace/pms/src/main/java/hdatuan/controller/ProfileController.java) (`/profile`), hiển thị thông tin và thống kê công việc của chính nhân viên đang đăng nhập.
* **Cập nhật tiến độ**: Hoàn thiện qua [ProfileEditController](file:///d:/WorkSpace/pms/src/main/java/hdatuan/controller/ProfileEditController.java) (`/profile-edit`), cho phép nhân viên cập nhật ngày bắt đầu, ngày kết thúc và trạng thái của tác vụ. *(Lưu ý: Có lỗ hổng bảo mật nghiêm trọng liên quan đến phân quyền).*

---

## II. Đánh giá chất lượng mã nguồn & Các điểm chưa hợp lý (Code Review & Issues)

Trong quá trình rà soát dự án, chúng tôi đã phát hiện một số điểm chưa hợp lý, lỗi logic và lỗ hổng bảo mật nghiêm trọng cần sửa đổi:

### 1. Lỗi Rò rỉ kết nối Cơ sở dữ liệu (Database Connection Leak) - *Trạng thái: Đã khắc phục (Resolved)*
* **Mô tả**: Trong các lớp Repository như [RoleRepository](file:///d:/WorkSpace/pms/src/main/java/hdatuan/repository/RoleRepository.java) (phương thức `findAll()`, `insertRole()`) và [JobRepository](file:///d:/WorkSpace/pms/src/main/java/hdatuan/repository/JobRepository.java) (phương thức `findAll()`), đối tượng `Connection` được mở nhưng **không bao giờ được đóng** (không sử dụng `try-with-resources` hay block `finally`).
* **Hậu quả**: Mỗi lần người dùng truy cập trang danh sách vai trò hoặc dự án, hệ thống sẽ mở thêm một kết nối mới tới MySQL và bỏ ngỏ. Chỉ sau vài lượt truy cập, MySQL sẽ báo lỗi `Too many connections` và làm sập toàn bộ ứng dụng.
* **Khắc phục**: Đã refactor toàn bộ các lớp Repository này để sử dụng cấu trúc `try-with-resources` giải phóng kết nối ngay sau khi sử dụng.

### 2. Lỗ hổng bảo mật phân quyền ngang (Insecure Direct Object Reference - IDOR) - *Mức độ: Nghiêm trọng (Critical)*
* **Mô tả**: Tại lớp [ProfileEditController](file:///d:/WorkSpace/pms/src/main/java/hdatuan/controller/ProfileEditController.java) (phương thức `doPost`), hệ thống cập nhật tác vụ dựa vào tham số `taskId` lấy trực tiếp từ request mà **không kiểm tra xem tác vụ đó có thực sự thuộc về người dùng đang đăng nhập hay không**.
* **Hậu quả**: Bất kỳ người dùng nào (kể cả Nhân viên thông thường) cũng có thể gửi một POST request tự chế với `taskId` bất kỳ để chỉnh sửa ngày bắt đầu, ngày kết thúc và trạng thái công việc của những người khác hoặc dự án khác.
* **Giải pháp**: Trước khi thực hiện cập nhật tác vụ, cần truy vấn thông tin tác vụ từ DB và xác nhận `task.getUser_id() == sessionUser.getId()`.

### 3. Lỗi mất thông báo phản hồi (Lost Redirect Messages) - *Mức độ: Trung bình (Medium)*
* **Mô tả**: Tại [RoleDeleteController](file:///d:/WorkSpace/pms/src/main/java/hdatuan/controller/RoleDeleteController.java), sau khi xóa vai trò thành công hoặc thất bại, hệ thống lưu thông điệp vào request: `req.setAttribute("deleteMessage", ...)` rồi thực hiện chuyển hướng `resp.sendRedirect(...)` về trang `/role`.
* **Hậu quả**: Lệnh `sendRedirect` khiến trình duyệt tạo một request mới hoàn toàn, do đó các thuộc tính (attribute) được đặt trong request cũ sẽ bị hủy bỏ. Kết quả là thông báo thành công/thất bại sẽ không bao giờ được hiển thị trên giao diện của `/role`.
* **Giải pháp**: Cần lưu thông báo này vào Session: `req.getSession().setAttribute("deleteMessage", ...)` trước khi chuyển hướng, tương tự như cách `RoleController` đang lấy thông điệp từ Session ra hiển thị.

### 4. Kiểm tra trùng lặp và hiệu năng kém trong `UserRepository` - *Trạng thái: Đã khắc phục (Resolved)*
* **Mô tả**:
  * Tại [UserRepository](file:///d:/WorkSpace/pms/src/main/java/hdatuan/repository/UserRepository.java) (phương thức `insertUser()`), hệ thống kiểm tra sự tồn tại của người dùng bằng cách so sánh `fullname` (`user.getFullname().equals(fullName)`) thay vì kiểm tra duy nhất theo `email`. Điều này khiến những người dùng khác nhau trùng tên không thể được tạo.
  * Việc tải toàn bộ danh sách người dùng (`this.findAll()`) lên bộ nhớ chỉ để kiểm tra sự tồn tại của một tên gọi gây lãng phí bộ nhớ và CPU khi cơ sở dữ liệu lớn lên.
* **Khắc phục**: Đã chuyển đổi logic kiểm tra trùng lặp sang truy vấn `SELECT COUNT(*) FROM users WHERE email = ?` trực tiếp dưới Database, vừa tối ưu hiệu năng vừa kiểm tra đúng theo địa chỉ email.

### 5. Việc phân chia Controller quá rời rạc (Redundant Controllers) - *Mức độ: Khuyến nghị Refactor (Refactor Recommend)*
* **Mô tả**: Các thao tác cho cùng một thực thể (ví dụ: `Role`) đang được chia nhỏ ra rất nhiều Servlet độc lập (`RoleController`, `RoleAddController`, `RoleDeleteController`). Điều này gây khó khăn cho việc quản lý mã nguồn và bảo trì cấu hình URL.
* **Giải pháp**: Nên gom nhóm các Servlet này lại thành một lớp duy nhất `RoleController` (hoặc `RoleServlet`) và điều hướng hành động thông qua phương thức HTTP (GET, POST, DELETE) hoặc thông qua tham số hành động (ví dụ `/role?action=add`).
