
Tài liệu này đánh giá hiện trạng phân quyền của dự án Project Management System (PMS) và đề xuất các giải pháp để hệ thống bảo mật, dễ bảo trì và chuyên nghiệp hơn.

---

## 1. Tình trạng Phân quyền Hiện tại (Current State)

Sau khi quét qua bộ mã nguồn, đây là cách hệ thống đang xử lý xác thực (Authentication) và phân quyền (Authorization):

- **Xác thực (Đăng nhập)**: Đã được thực hiện tốt thông qua `AuthenticationFilter`. Bất cứ request nào chưa có session hợp lệ đều bị đá về trang `/login`. Các file tĩnh (CSS/JS) được cho phép đi qua.
- **Phân quyền (Bảo vệ Route)**: Được thực hiện **thủ công (hardcode)** bên trong từng Servlet/Controller. Ví dụ:
  ```java
  // Bên trong UserAddController.java, RoleAddController.java...
  if (user == null || user.getRoleID() != 1) {
      resp.sendRedirect(req.getContextPath() + "/404");
      return;
  }
  ```
- **Phân quyền UI (View/JSP)**: Các nút bấm (Sửa, Xóa, Thêm mới) được ẩn/hiện bằng JSTL với điều kiện cứng kiểm tra số ID của role:
  ```jsp
  <c:if test="${sessionScope.user.roleID == 1}">
      <!-- Hiển thị nút Sửa / Xóa -->
  </c:if>
  ```

---

## 2. Những Hạn chế & Rủi ro (Weaknesses)

Hệ thống hiện tại tuy đã hoạt động nhưng bộc lộ nhiều điểm yếu nếu dự án scale lên:

1. **Hardcode "Magic Numbers"**: Số `1` được gán cứng có nghĩa là `ADMIN`. Nếu sau này DB thay đổi cấu trúc, hoặc cần thêm Role mới (ví dụ SuperAdmin có ID là 4), developer sẽ phải tìm và sửa thủ công ở hàng chục file Controller và JSP khác nhau.
2. **Logic Phân tán (Vi phạm DRY - Don't Repeat Yourself)**: Logic phân quyền đang bị nhồi nhét vào từng method `doGet`/`doPost` của Controller.
   - *Rủi ro*: Khi một Developer khác thêm một Controller mới (ví dụ `JobAddController`), họ có thể **quên** viết đoạn code check quyền này, tạo ra lỗ hổng bảo mật nghiêm trọng (bất kỳ user nào cũng truy cập được).
3. **Filter chưa làm tròn vai**: Filter hiện tại chỉ mới làm được "Authentication" (Có phải là người dùng hệ thống không?) chứ chưa làm "Authorization" (Người dùng này được phép đi vào đâu?).

---

## 3. Đề xuất Nâng cấp (Best Practices / What to do next)

Để hệ thống chuẩn mực và chuyên nghiệp hơn, dưới đây là lộ trình Refactor nên áp dụng:

### Bước 1: Loại bỏ "Magic Numbers" bằng Constants / Enums
Tạo một class `RoleConstants` hoặc Enum để quản lý các Role.
```java
public class RoleConstants {
    public static final int ROLE_ADMIN = 1;
    public static final int ROLE_MANAGER = 2;
    public static final int ROLE_STAFF = 3;
}
```
*Tác dụng*: Đọc code dễ hiểu hơn (VD: `user.getRoleID() == RoleConstants.ROLE_ADMIN`) và dễ dàng thay đổi ID ở một nơi duy nhất.

### Bước 2: Dời logic Phân quyền lên Filter (Authorization Filter)
Tạo một `AuthorizationFilter` riêng biệt (chạy sau `AuthenticationFilter`) hoặc gộp chung vào để chặn request ngay từ vòng ngoài. Không để request lọt vào tới Controller mới check.
```java
// Logic giả mã (Pseudocode) bên trong Filter:
String path = req.getServletPath();
int role = user.getRoleID();

if (path.startsWith("/user-add") || path.startsWith("/role-edit")) {
    if (role != RoleConstants.ROLE_ADMIN) {
        resp.sendRedirect("/403"); // Nên dùng 403 (Forbidden) thay vì 404 (Not Found)
        return;
    }
}
```
*Tác dụng*: Tập trung quản lý toàn bộ luồng phân quyền ở một file duy nhất. Các Controller lúc này sẽ hoàn toàn sạch sẽ, chỉ tập trung vào xử lý nghiệp vụ (Business Logic).

### Bước 3: Làm sạch tầng View (JSP)
Thay vì check số trên JSP, hãy truyền các biến cờ (boolean) từ Filter hoặc Controller xuống.
```java
// Set trong Filter hoặc Session:
req.setAttribute("isAdmin", user.getRoleID() == RoleConstants.ROLE_ADMIN);
```
Trên JSP chỉ cần gọi:
```jsp
<c:if test="${isAdmin}">
    <!-- Hiện nút -->
</c:if>
```
*Tác dụng*: Tách bạch hoàn toàn HTML và logic của DB.

### Bước 4: Chuyển sang mô hình Role-Permission (Định hướng tương lai xa)
Hiện tại hệ thống phân quyền theo Role (Vai trò). Nếu hệ thống phức tạp hơn, có thể nâng cấp thành bảng Map giữa Role và Permission (Quyền hạn).
*Ví dụ*: Thay vì check `if (isAdmin)`, hệ thống sẽ check `if (user.hasPermission("USER_DELETE"))`. Điều này cho phép một tài khoản Manager cũng có thể được gán quyền xóa User nếu cần, mà không phải sửa code.
