# Hướng dẫn khởi chạy dự án tại Local (CLI Approach)

Tài liệu này hướng dẫn cách chạy dự án Project Management System (PMS) theo chuẩn best practice, ưu tiên sử dụng Command Line Interface (CLI) thay vì phụ thuộc vào cấu hình của các IDE (như IntelliJ, Eclipse). Việc này giúp đồng nhất môi trường giữa các thành viên và dễ dàng đưa lên quy trình CI/CD sau này.

## Yêu cầu môi trường (Prerequisites)
Đảm bảo máy tính của bạn đã cài đặt các công cụ sau và đã thêm vào biến môi trường (PATH):
1. **Java JDK 21**: Kiểm tra bằng lệnh `java -version`
2. **Maven (3.8+)**: Kiểm tra bằng lệnh `mvn -version`
3. **Docker & Docker Compose**: Kiểm tra bằng lệnh `docker -v` (Dùng để chạy Database cô lập, sạch sẽ)

---

## Bước 1: Khởi tạo Database với Docker

Thay vì cài đặt MySQL Server trực tiếp lên máy (có thể gây rác hệ thống hoặc xung đột port), chúng ta dùng Docker để chạy một container MySQL riêng biệt.

Mở Terminal (Command Prompt / PowerShell) và chạy lệnh sau để khởi tạo container MySQL 8.0:

```bash
docker run --name pms-mysql -e MYSQL_ROOT_PASSWORD=admin -e MYSQL_DATABASE=pms -p 3306:3306 -d mysql:8.0
```

*Giải thích:*
- `--name pms-mysql`: Tên của container.
- `-e MYSQL_ROOT_PASSWORD=admin`: Đặt mật khẩu root là `admin`.
- `-e MYSQL_DATABASE=pms`: Tự động tạo database có tên là `pms`.
- `-p 3306:3306`: Map port 3306 của container ra port 3306 của máy host.
- `-d`: Chạy ngầm (detached mode).

Sau khi container đã chạy, bạn cần import dữ liệu mẫu. Nếu bạn đang ở thư mục gốc của dự án (`d:\WorkSpace\pms`), hãy chạy lệnh sau để đẩy file `schema.sql` vào container:

```bash
docker exec -i pms-mysql mysql -uroot -padmin pms < database/schema.sql
```

## Bước 2: Cấu hình kết nối Database

Đảm bảo file cấu hình kết nối của dự án trỏ đúng vào Database vừa tạo.
Mở file `src/main/resources/db.properties` (tạo từ file `.example` nếu chưa có) và cập nhật thông tin:

```properties
db.url=jdbc:mysql://localhost:3306/pms
db.user=root
db.password=admin
```

## Bước 3: Build và Chạy ứng dụng bằng Maven

Dự án này đã được tích hợp sẵn `cargo-maven3-plugin` trong `pom.xml`, cho phép tự động tải và nhúng Apache Tomcat 9.0 để chạy ứng dụng mà không cần bạn phải tự tải và cấu hình Tomcat thủ công.

Tại thư mục gốc của dự án (nơi chứa `pom.xml`), chạy chuỗi lệnh sau:

**1. Dọn dẹp và Build project ra file WAR:**
```bash
mvn clean package
```

**2. Khởi chạy Server (Tomcat) qua Cargo Plugin:**
```bash
mvn cargo:run
```

*Lưu ý: Lần chạy đầu tiên sẽ hơi lâu do Maven phải tải Tomcat 9.0 zip về máy.*

Khi bạn thấy dòng log tương tự như `[INFO] [talledLocalContainer] Tomcat 9.x started on port [8080]`, nghĩa là server đã sẵn sàng.

## Bước 4: Truy cập ứng dụng

Mở trình duyệt và truy cập vào địa chỉ:
👉 **http://localhost:8080/pms** *(Lưu ý context-path có thể là `/` hoặc `/pms` tùy thuộc vào cấu hình artifact, hãy xem log của Tomcat)*.

**Tài khoản đăng nhập mẫu:**
- Admin: `admin@gmail.com` / `123456`
- Manager: `manager01@gmail.com` / `123456`
- Staff: `staff.dev01@gmail.com` / `123456`

---

## Các lệnh thao tác nhanh (Cheat Sheet)

- **Dừng server:** Nhấn `Ctrl + C` tại cửa sổ Terminal đang chạy `mvn cargo:run`.
- **Dừng Database:** `docker stop pms-mysql`
- **Khởi động lại Database:** `docker start pms-mysql`
- **Xóa hoàn toàn Database:** `docker rm -f pms-mysql`
