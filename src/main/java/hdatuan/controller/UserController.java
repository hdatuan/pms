package hdatuan.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hdatuan.enums.UserRole;
import hdatuan.service.UserService;
import hdatuan.entity.User;

@WebServlet(name = "userController", urlPatterns = { "/user", "/user-delete" })
public class UserController extends HttpServlet {

	UserService userService = new UserService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(false);
		User sessionUser = session != null ? (User) session.getAttribute("user") : null;

		if (req.getServletPath().equals("/user-delete")) {
			int user_id = Integer.parseInt(req.getParameter("id"));
			boolean success = userService.deleteUser(user_id);
			if (session != null) {
				session.setAttribute("deleteMessage", success ? "Xóa người dùng thành công!" : "Xóa người dùng thất bại!");
				session.setAttribute("isSuccess", success);
			}
			resp.sendRedirect(req.getContextPath() + "/user");
			return;
		}

		// Đọc flash message từ session (nếu có sau khi redirect từ user-delete)
		if (session != null) {
			Object deleteMsg = session.getAttribute("deleteMessage");
			if (deleteMsg != null) {
				req.setAttribute("deleteMessage", deleteMsg);
				req.setAttribute("isSuccess", session.getAttribute("isSuccess"));
				session.removeAttribute("deleteMessage");
				session.removeAttribute("isSuccess");
			}
		}

		List<User> userList = userService.getAllUsers();
		boolean isAdmin = sessionUser != null && sessionUser.getRoleID() == UserRole.ADMIN.getId();

		req.setAttribute("users", userList);
		req.setAttribute("isAdmin", isAdmin);

		req.getRequestDispatcher("/WEB-INF/views/user-table.jsp").forward(req, resp);
	}

}
