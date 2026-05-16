# Kiến trúc hệ thống (System Architecture)

Dự án PMS tuân thủ chặt chẽ mô hình **MVC (Model - View - Controller)** kết hợp với kiến trúc nhiều lớp (N-Tier Architecture).

## 1. Các Module Chính (Thư mục)
- `src/main/java/hdatuan/controller/`: Chứa các Servlet đóng vai trò Controller, xử lý HTTP request/response.
- `src/main/java/hdatuan/service/`: Chứa logic nghiệp vụ (Business logic).
- `src/main/java/hdatuan/repository/`: Chứa các class thao tác với Database qua JDBC (Data Access Layer).
- `src/main/java/hdatuan/entity/`: Chứa các class POJO (Model) đại diện cho các bảng trong DB.
- `src/main/java/hdatuan/filter/`: Chứa các bộ lọc (như `AuthenticationFilter`) để bảo vệ các tuyến đường (routes).
- `src/main/java/hdatuan/config/`: Cấu hình kết nối Database.
- `src/main/webapp/WEB-INF/views/`: Chứa các file JSP (View).

## 2. Luồng Xử Lý Dữ Liệu (Request/Response Flow)
1. **Client (Browser)** gửi Request (ví dụ GET `/user`).
2. **Filter (`AuthenticationFilter`)** chặn request để kiểm tra session (đã đăng nhập chưa, có quyền truy cập không). Nếu hợp lệ, cho đi tiếp.
3. **Controller (`UserController`)** tiếp nhận request, lấy dữ liệu từ `HttpServletRequest`.
4. **Controller** gọi phương thức của **Service (`UserService`)**.
5. **Service** thực hiện logic và gọi **Repository (`UserRepository`)**.
6. **Repository** mở kết nối DB qua `MySQLConfig`, chạy câu SQL, map kết quả vào các **Entity** và trả về List/Object cho Service.
7. **Controller** nhận kết quả từ Service, set vào `req.setAttribute()`.
8. **Controller** chuyển hướng (forward) tới `JSP` tương ứng.
9. **JSP** (với JSTL) render HTML và trả về cho Client.

## 3. Luồng Xác thực (Authentication Flow)
- Gửi POST đến `/login` với email/mật khẩu.
- `LoginController` gọi `UserService` kiểm tra thông tin.
- Nếu thành công, lấy thông tin User và lưu vào `HttpSession`.
- Các request sau đó tới các trang quản trị (như `/home`, `/user`, `/role`) đều bị `AuthenticationFilter` kiểm tra. Nếu không có session, redirect về `/login`.
