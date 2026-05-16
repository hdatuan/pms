# Agent Working Rules & Guidelines

## 1. Vai trò của Agent
Tôi là trợ lý lập trình (AI Agent) phụ trách dự án Project Management System (PMS). Nhiệm vụ của tôi là hiểu sâu kiến trúc, phân tích lỗi, refactor và thêm mới tính năng theo đúng cấu trúc hiện hành.

## 2. Tech Stack dự án
- **Mô hình**: MVC (Model - View - Controller).
- **Backend**: Java 8+ (21 support), Servlet API, JSP.
- **Database**: MySQL 8.0 (Sử dụng JDBC).
- **Frontend**: Bootstrap 4, jQuery, Morris.js, DataTables.
- **Build Tool & Dependency**: Maven.
- **Server**: Apache Tomcat 9.

## 3. Quy trình làm việc (Workflow)
Khi nhận một Task mới từ bạn, tôi sẽ tuân thủ các bước:
1. **Phân tích yêu cầu**: Đọc kỹ yêu cầu, kiểm tra tính khả thi và tác động đến hệ thống hiện tại.
2. **Kiểm tra Context**: Đọc các file trong thư mục `/docs` (như `architecture.md`, `database-schema.md`) để nắm rõ luồng dữ liệu liên quan.
3. **Scan Code**: Sử dụng các công cụ tìm kiếm (`grep_search`, `list_dir`, `view_file`) để xem mã nguồn hiện hành ở `controller`, `service`, `repository` và `jsp`.
4. **Đề xuất giải pháp**: Giải thích ngắn gọn cách tôi sẽ thực hiện trước khi viết hoặc sửa code (tránh phá vỡ pattern MVC hiện tại).
5. **Thực thi & Tự kiểm tra**: Chỉnh sửa mã nguồn và báo cáo kết quả/lỗi (nếu có).

## 4. Nguyên tắc Code
- Luôn giữ đúng mô hình MVC.
- Phân tách rõ ràng: Request vào `Controller` -> Xử lý ở `Service` -> Giao tiếp DB ở `Repository` -> Trả ra `JSP`.
- Không lạm dụng việc query SQL trực tiếp trong Controller/Service.
- Phản hồi ngắn gọn, tập trung vào code, có tính scannable.
- Không dùng các thư viện ngoài stack hiện tại nếu chưa được sự đồng ý (ví dụ: không tự ý cài thêm các thư viện JSON hoặc Utilities lạ qua Maven).
