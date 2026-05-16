package hdatuan.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hdatuan.config.MySQLConfig;
import hdatuan.service.LoginService;
import hdatuan.service.UserService;
import hdatuan.entity.User;

@WebServlet(name = "loginController", urlPatterns = { "/login" })
public class LoginController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Nếu đã đăng nhập thì không thể vào trang /login nữa
		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute("user") != null) {
			resp.sendRedirect(req.getContextPath() + "/home");
			return;
		}

		String email = "";
		Cookie[] cookieList = req.getCookies();

		if (cookieList != null) {
			for (Cookie cookie : cookieList) {
				if ("email".equals(cookie.getName())) {
					email = cookie.getValue();
					break;
				}
			}
		}

		req.setAttribute("email", email);
		req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String remember = req.getParameter("remember");
		System.out.println("on".equals(remember) ? "Nhớ mật khẩu" : "Không nhớ mật khẩu");

		LoginService loginService = new LoginService();
		User user = loginService.findUser(email, password);

		if (user != null) {
			HttpSession session = req.getSession();
			session.setAttribute("user", user);

			if (remember != null) {
				Cookie cEmail = new Cookie("email", email);
				cEmail.setMaxAge(60 * 60 * 24 * 7); // Lưu cookie 7 ngày nếu tick "Nhớ mật khẩu"
				resp.addCookie(cEmail);
			} else {
				// Nếu không tick "Nhớ mật khẩu", xóa cookie cũ nếu có
				// Tạo mới cookie hoặc ghi đè cookie cũ, thời gian tồn tại là 0
				Cookie cEmail = new Cookie("email", "");
				cEmail.setMaxAge(0);
				resp.addCookie(cEmail);
			}

			resp.sendRedirect(req.getContextPath() + "/home");
		} else {
			req.setAttribute("loginResult", "Sai email hoặc mật khẩu");
			req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
		}
	}

}
