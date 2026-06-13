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

Sau khi container đã chạy, bạn cần import dữ liệu mẫu. Nếu bạn đang ở thư mục gốc của dự án (`d:\WorkSpace\pms`), hãy chạy lệnh sau để đẩy file `pms.sql` vào container:

```bash
docker exec -i pms-mysql mysql -uroot -padmin pms < database/pms.sql
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

## Hướng dẫn Khởi chạy bằng Docker Compose (Khuyên dùng)

Nếu bạn đã cài đặt **Docker & Docker Desktop**, đây là cách nhanh nhất và chuẩn hóa nhất để chạy dự án mà không cần phải cài đặt thủ công Java, Maven hay Apache Tomcat trên máy host.

### Các bước thực hiện:

1. **Khởi động Docker Desktop** trên máy tính của bạn.
2. **Cấu hình môi trường (.env)**:
   - Tạo một bản sao từ file `.env.example` ở thư mục gốc của dự án và đổi tên thành `.env`.
   - Mở file `.env` và tùy chỉnh tên database (`MYSQL_DATABASE`), mật khẩu Root (`MYSQL_ROOT_PASSWORD`), cổng MySQL trên máy host (`MYSQL_PORT`), tài khoản đăng nhập DB của ứng dụng (`DB_USER`, `DB_PASS`) theo nhu cầu của bạn.
3. **Khởi chạy hệ thống bằng Docker Compose**:
   Tại thư mục gốc của dự án (nơi chứa file `docker-compose.yml` và `.env`), chạy lệnh sau trong PowerShell/CMD hoặc Terminal:
   ```bash
   docker compose up --build -d
   ```
   *Lệnh này sẽ:*
   - Xây dựng file WAR của ứng dụng bên trong container builder (Java 21).
   - Tải và cấu hình MySQL 8.0, tự động import dữ liệu mẫu từ `database/pms.sql`.
   - Tải và cấu hình Apache Tomcat 9, deploy ứng dụng dưới dạng thư mục gốc (ROOT).
   - Chạy ngầm toàn bộ các dịch vụ (`-d`).

3. **Truy cập ứng dụng**:
   Mở trình duyệt và truy cập vào địa chỉ:
   👉 **http://localhost:8080/**
   *(Lưu ý: Vì được deploy dưới dạng `ROOT.war` trong container, bạn có thể truy cập thẳng qua port 8080 mà không cần hậu tố `/pms`)*.

4. **Dừng hệ thống**:
   Để dừng toàn bộ các container và giữ nguyên dữ liệu, chạy lệnh:
   ```bash
   docker compose down
   ```
   Để xóa sạch dữ liệu (bao gồm cả database volumes), chạy lệnh:
   ```bash
   docker compose down -v
   ```

---

## Các lệnh thao tác nhanh (Cheat Sheet)

- **Dừng server (Chạy thủ công):** Nhấn `Ctrl + C` tại cửa sổ Terminal đang chạy `mvn cargo:run`.
- **Dừng Database (Chạy thủ công):** `docker stop pms-mysql`
- **Khởi động lại Database (Chạy thủ công):** `docker start pms-mysql`
- **Xóa hoàn toàn Database (Chạy thủ công):** `docker rm -f pms-mysql`
