-- =====================================================
-- Project Management System
-- Database: MySQL 8+
-- =====================================================

DROP DATABASE IF EXISTS pms;
CREATE DATABASE pms
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE pms;

-- -----------------------------------------------------
-- 1) ROLES
-- -----------------------------------------------------
CREATE TABLE roles (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  description VARCHAR(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uk_roles_name (name)
) ENGINE=InnoDB;

-- -----------------------------------------------------
-- 2) USERS
-- -----------------------------------------------------
CREATE TABLE users (
  id INT NOT NULL AUTO_INCREMENT,
  email VARCHAR(100) NOT NULL,
  password VARCHAR(255) NOT NULL,
  fullname VARCHAR(100) NOT NULL,
  role_id INT NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uk_users_email (email),
  KEY idx_users_role_id (role_id),
  CONSTRAINT fk_users_roles
    FOREIGN KEY (role_id) REFERENCES roles(id)
    ON UPDATE CASCADE
    ON DELETE RESTRICT
) ENGINE=InnoDB;

-- -----------------------------------------------------
-- 3) JOBS
-- -----------------------------------------------------
CREATE TABLE jobs (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(150) NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uk_jobs_name (name),
  CONSTRAINT chk_jobs_date_range CHECK (end_date >= start_date)
) ENGINE=InnoDB;

-- -----------------------------------------------------
-- 4) STATUS
-- -----------------------------------------------------
CREATE TABLE status (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uk_status_name (name)
) ENGINE=InnoDB;

-- -----------------------------------------------------
-- 5) TASKS
-- -----------------------------------------------------
CREATE TABLE tasks (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(200) NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  user_id INT NOT NULL,
  job_id INT NOT NULL,
  status_id INT NOT NULL,
  PRIMARY KEY (id),
  KEY idx_tasks_user_id (user_id),
  KEY idx_tasks_job_id (job_id),
  KEY idx_tasks_status_id (status_id),
  CONSTRAINT chk_tasks_date_range CHECK (end_date >= start_date),
  CONSTRAINT fk_tasks_users
    FOREIGN KEY (user_id) REFERENCES users(id)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,
  CONSTRAINT fk_tasks_jobs
    FOREIGN KEY (job_id) REFERENCES jobs(id)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,
  CONSTRAINT fk_tasks_status
    FOREIGN KEY (status_id) REFERENCES status(id)
    ON UPDATE CASCADE
    ON DELETE RESTRICT
) ENGINE=InnoDB;

-- =====================================================
-- SAMPLE DATA (10 rows each table)
-- =====================================================

-- ROLES (10)
INSERT INTO roles (id, name, description) VALUES
(1, 'ADMIN', 'Quản trị hệ thống'),
(2, 'MANAGER', 'Quản lý dự án và nhân sự'),
(3, 'STAFF', 'Nhân viên thực hiện công việc');

-- USERS (10)
INSERT INTO users (id, email, password, fullname, role_id) VALUES
(1, 'admin@gmail.com', '123456', 'Admin đẹp trai', 1),
(2, 'manager01@gmail.com', '123456', 'Nguyễn Minh Quân', 2),
(3, 'staff.dev01@gmail.com', '123456', 'Phạm Gia Khang', 3);

-- JOBS (10)
INSERT INTO jobs (id, name, start_date, end_date) VALUES
(1, 'CRM Core Platform', '2026-01-01', '2026-06-30'),
(2, 'Customer Data Migration', '2026-01-15', '2026-04-30'),
(3, 'Sales Pipeline Automation', '2026-02-01', '2026-07-31'),
(4, 'Marketing Analytics Dashboard', '2026-02-10', '2026-05-31'),
(5, 'Support Ticket Integration', '2026-03-01', '2026-08-15'),
(6, 'Mobile App Companion', '2026-03-20', '2026-09-30'),
(7, 'Finance Report Standardization', '2026-01-20', '2026-05-20'),
(8, 'HR Workflow Digitization', '2026-02-05', '2026-06-15'),
(9, 'Client Onboarding Revamp', '2026-04-01', '2026-10-31'),
(10, 'Performance Optimization Sprint', '2026-05-01', '2026-12-15');

-- STATUS (10)
INSERT INTO status (id, name) VALUES
(1, 'Chưa thực hiện'),
(2, 'Đang thực hiện'),
(3, 'Đã thực hiện');

-- TASKS (10)
INSERT INTO tasks (id, name, start_date, end_date, user_id, job_id, status_id) VALUES
(1, 'Thiết kế schema người dùng', '2026-01-05', '2026-01-20', 1, 1, 3),
(2, 'Nhập liệu khách hàng mẫu', '2026-01-18', '2026-02-05', 2, 2, 2),
(3, 'Xây dựng API đồng bộ lead', '2026-02-03', '2026-03-10', 3, 3, 2),
(4, 'Tạo dashboard tổng hợp KPI', '2026-02-15', '2026-03-25', 1, 4, 1),
(5, 'Kết nối ticket support', '2026-03-05', '2026-04-20', 2, 5, 2),
(6, 'Phát triển giao diện mobile', '2026-03-25', '2026-05-30', 3, 6, 1),
(7, 'Chuẩn hóa báo cáo doanh thu', '2026-02-01', '2026-03-15', 1, 7, 3),
(8, 'Sơ hóa quy trình onboarding HR', '2026-02-10', '2026-04-10', 2, 8, 2),
(9, 'Xây dựng checklist onboarding', '2026-04-05', '2026-05-15', 3, 9, 1),
(10, 'Tối ưu truy vấn tổng hợp', '2026-05-05', '2026-06-20', 1, 10, 2);




