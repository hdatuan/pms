# Tài liệu Phân quyền Hệ thống (Roles & Permissions)

Tài liệu này mô tả chi tiết cơ chế phân quyền (Role-Based Access Control - RBAC) đang được áp dụng trong dự án Project Management System (PMS). 

## 1. Cơ chế hoạt động (Kiến trúc Filter)

Hệ thống sử dụng mô hình bảo mật 2 lớp thông qua Java Servlet Filters, đảm bảo tính tập trung và dễ bảo trì:

1. **Lớp 1: Xác thực (`AuthenticationFilter`)**
   - Đảm bảo người dùng phải có phiên đăng nhập (Session) hợp lệ.
   - Nếu chưa đăng nhập, mọi request (trừ các route public như `/login`, tài nguyên tĩnh) đều bị chuyển hướng về trang đăng nhập.
2. **Lớp 2: Phân quyền (`AuthorizationFilter`)**
   - Chạy ngay sau khi xác thực thành công.
   - Kiểm tra **Role ID** của người dùng hiện tại đối chiếu với danh sách các URL được bảo vệ.
   - Nếu không đủ thẩm quyền, hệ thống sẽ từ chối truy cập và chuyển hướng người dùng đến trang lỗi **403 Forbidden**.

*(Thứ tự chạy của các Filter được đảm bảo tuyệt đối thông qua cấu hình trong `web.xml`)*

---

## 2. Danh sách Vai trò (Roles)

Hệ thống hiện tại định nghĩa 3 cấp độ phân quyền, được quản lý chặt chẽ qua `UserRole` Enum:

| Role ID | Tên Vai trò | Mô tả |
|:---:|:---|:---|
| `1` | **Admin** (Quản trị viên) | Quyền lực cao nhất, có toàn quyền quản trị toàn bộ hệ thống, quản lý người dùng, phân quyền và các dự án. |
| `2` | **Manager** (Quản lý) | Chịu trách nhiệm quản lý dự án cụ thể. Được phép thao tác trên các dự án mình quản lý và nhân sự thuộc dự án đó. |
| `3` | **Staff** (Nhân viên) | Chỉ có quyền xem thông tin cơ bản, thực hiện các công việc (task) được giao. |

---

## 3. Bảng Phân quyền Chức năng (Permission Matrix)

Dưới đây là ma trận phân quyền chi tiết cho các chức năng trong hệ thống:

### Nhóm Quản trị Hệ thống (Admin-Only)
*Chỉ dành riêng cho Admin. Manager và Staff không thể truy cập.*

| Chức năng | Đường dẫn (Route) | Admin | Manager | Staff |
|:---|:---|:---:|:---:|:---:|
| Thêm người dùng mới | `/user-add` | ✅ | ❌ | ❌ |
| Chỉnh sửa người dùng | `/user-edit` | ✅ | ❌ | ❌ |
| Xóa người dùng | `/user-delete` | ✅ | ❌ | ❌ |
| Thêm quyền (Role) mới | `/role-add` | ✅ | ❌ | ❌ |
| Chỉnh sửa quyền | `/role-edit` | ✅ | ❌ | ❌ |
| Xóa quyền | `/role-delete` | ✅ | ❌ | ❌ |

### Nhóm Quản lý Dự án & Công việc
*Dành cho Admin và Manager. Staff bị giới hạn.*

| Chức năng | Đường dẫn (Route) | Admin | Manager | Staff |
|:---|:---|:---:|:---:|:---:|
| Tạo mới dự án / nhóm việc | `/groupwork-add` | ✅ | ✅ | ❌ |
| Chỉnh sửa dự án | `/groupwork-edit` | ✅ | ✅ | ❌ |
| Tạo Task (giao việc) | `/task-add` | ✅ | ✅ | ❌ |
| Chỉnh sửa Task | `/task-edit` | ✅ | ✅ | ❌ |
| Cập nhật trạng thái Task | *Chưa định nghĩa URL* | ✅ | ✅ | ❌ |
| Quản lý thành viên dự án (Thêm/Xóa) | *Chưa định nghĩa URL* | ✅ | ✅ | ❌ |
| Xem tiến độ dự án | *Chưa định nghĩa URL* | ✅ | ✅ | ❌ |

### Nhóm Truy cập Chung
*Dành cho tất cả người dùng đã đăng nhập hệ thống.*

| Chức năng | Đường dẫn (Route) | Admin | Manager | Staff |
|:---|:---|:---:|:---:|:---:|
| Bảng điều khiển (Dashboard) | `/home` | ✅ | ✅ | ✅ |
| Xem hồ sơ cá nhân | `/profile` | ✅ | ✅ | ✅ |
| Cập nhật hồ sơ cá nhân | `/profile-edit` | ✅ | ✅ | ✅ |
| Xem danh sách thành viên | `/user` | ✅ | ✅ | ✅ |
| Xem danh sách dự án | `/groupwork` | ✅ | ✅ | ✅ |
| Xem danh sách công việc | `/task` | ✅ | ✅ | ✅ |
| Đăng xuất | `/logout` | ✅ | ✅ | ✅ |

---

## 4. Hướng dẫn Dành cho Developer (Bổ sung Quyền)

Khi phát triển thêm tính năng mới và cần khóa đường dẫn (route) tương ứng, bạn không cần viết code kiểm tra vào Controller. Chỉ cần:

1. Mở file `src/main/java/hdatuan/filter/AuthorizationFilter.java`.
2. Thêm đường dẫn (ví dụ: `"/my-new-feature"`) vào một trong hai mảng sau:
   - `ADMIN_ONLY_PATHS`: Nếu chỉ cho phép Admin.
   - `ADMIN_AND_MANAGER_PATHS`: Nếu cho phép cả Admin và Manager.

Mọi tác vụ từ chối quyền truy cập (redirect về trang 403) sẽ được `AuthorizationFilter` tự động xử lý. Tầng View (JSP) có thể lấy biến boolean từ session hoặc request để ẩn/hiện các nút (Button) tương ứng trên giao diện (Ví dụ: `<c:if test="${isAdmin}"> ... </c:if>`).
