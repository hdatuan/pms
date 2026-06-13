package hdatuan.config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class MySQLConfig {

	public static Connection getConnection() {
		Connection connection = null;
		Properties prop = new Properties();
		try (InputStream input = MySQLConfig.class.getClassLoader().getResourceAsStream("db.properties")) {
			String url = null;
			String username = null;
			String password = null;

			if (input != null) {
				prop.load(input);
				url = prop.getProperty("db.url");
				username = prop.getProperty("db.user");
				password = prop.getProperty("db.pass");
			} else {
				System.out.println("Không tìm thấy file db.properties, sẽ thử đọc từ biến môi trường.");
			}

			String envUrl = System.getenv("DB_URL");
			String envUser = System.getenv("DB_USER");
			String envPass = System.getenv("DB_PASS");

			if (envUrl != null && !envUrl.trim().isEmpty()) {
				url = envUrl;
			}
			if (envUser != null && !envUser.trim().isEmpty()) {
				username = envUser;
			}
			if (envPass != null && !envPass.trim().isEmpty()) {
				password = envPass;
			}

			if (url == null || username == null) {
				System.out.println("Lỗi: Không tìm thấy cấu hình Database (db.properties hoặc Env Variables).");
				return null;
			}

			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, username, password);
			if (connection != null) {
				try (java.sql.Statement stmt = connection.createStatement()) {
					stmt.execute("SET NAMES 'utf8mb4' COLLATE 'utf8mb4_unicode_ci'");
				}
			}
		} catch (Exception e) {
			System.out.println("Lỗi kết nối : " + e.getMessage());
		}
		return connection;
	}
}
