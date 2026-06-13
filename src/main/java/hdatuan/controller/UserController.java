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
import hdatuan.service.RoleService;
import hdatuan.entity.User;
import hdatuan.entity.Role;

@WebServlet(name = "userController", urlPatterns = { "/user", "/user-add", "/user-edit", "/user-delete" })
public class UserController extends HttpServlet {

	private UserService userService = new UserService();
	private RoleService roleService = new RoleService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String servletPath = req.getServletPath();
		HttpSession session = req.getSession(false);
		User sessionUser = session != null ? (User) session.getAttribute("user") : null;

		if (servletPath.equals("/user-delete")) {
			int user_id = Integer.parseInt(req.getParameter("id"));
			boolean success = userService.deleteUser(user_id);
			if (session != null) {
				session.setAttribute("deleteMessage", success ? "Xóa người dùng thành công!" : "Xóa người dùng thất bại!");
				session.setAttribute("isSuccess", success);
			}
			resp.sendRedirect(req.getContextPath() + "/user");
			return;
		}

		if (servletPath.equals("/user-add") || servletPath.equals("/user-edit")) {
			List<Role> roles = roleService.getAllRoles();
			req.setAttribute("roles", roles);
			if (servletPath.equals("/user-edit")) {
				String idStr = req.getParameter("id");
				if (idStr == null || idStr.trim().isEmpty()) {
					resp.sendRedirect(req.getContextPath() + "/user");
					return;
				}
				int id = Integer.parseInt(idStr);
				User editUser = userService.findById(id);
				if (editUser == null) {
					resp.sendRedirect(req.getContextPath() + "/user");
					return;
				}
				req.setAttribute("editUser", editUser);
				req.getRequestDispatcher("/WEB-INF/views/user-edit.jsp").forward(req, resp);
				return;
			}
			req.getRequestDispatcher("/WEB-INF/views/user-add.jsp").forward(req, resp);
			return;
		}

		// Đọc flash message từ session (nếu có sau khi redirect từ user-delete/add/edit)
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

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String servletPath = req.getServletPath();
		String fullName = req.getParameter("fullname");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		int roleId = Integer.parseInt(req.getParameter("role-id"));
		boolean isDone = true;

		boolean isEditMode = servletPath.equals("/user-edit");
		if (fullName == null || fullName.trim().isEmpty() || email == null || email.trim().isEmpty()) {
			req.setAttribute("message", "Họ tên và Email không được để trống!");
			req.setAttribute("isDone", isDone);
			req.setAttribute("isSuccess", false);
			req.setAttribute("roles", roleService.getAllRoles());
			if (isEditMode) {
				String idStr = req.getParameter("id");
				if (idStr != null && !idStr.isEmpty()) {
					req.setAttribute("editUser", userService.findById(Integer.parseInt(idStr)));
				}
			}
			req.getRequestDispatcher(isEditMode ? "/WEB-INF/views/user-edit.jsp" : "/WEB-INF/views/user-add.jsp")
					.forward(req, resp);
			return;
		}
		if (!isEditMode && (password == null || password.trim().isEmpty())) {
			req.setAttribute("message", "Mật khẩu không được để trống!");
			req.setAttribute("isDone", isDone);
			req.setAttribute("isSuccess", false);
			req.setAttribute("roles", roleService.getAllRoles());
			req.getRequestDispatcher("/WEB-INF/views/user-add.jsp").forward(req, resp);
			return;
		}

		HttpSession session = req.getSession();

		if (servletPath.equals("/user-add")) {
			boolean isSuccess = userService.insertUser(fullName, email, password, roleId);
			if (isSuccess) {
				if (session != null) {
					session.setAttribute("deleteMessage", "Thêm người dùng thành công!");
					session.setAttribute("isSuccess", true);
				}
				resp.sendRedirect(req.getContextPath() + "/user");
			} else {
				req.setAttribute("message", "Người dùng đã tồn tại, vui lòng thử lại!");
				req.setAttribute("isDone", isDone);
				req.setAttribute("isSuccess", false);
				req.setAttribute("roles", roleService.getAllRoles());
				req.getRequestDispatcher("/WEB-INF/views/user-add.jsp").forward(req, resp);
			}
		} else if (servletPath.equals("/user-edit")) {
			int id = Integer.parseInt(req.getParameter("id"));
			boolean isSuccess = userService.updateUser(id, fullName, email, password, roleId);
			if (isSuccess) {
				if (session != null) {
					session.setAttribute("deleteMessage", "Chỉnh sửa người dùng thành công!");
					session.setAttribute("isSuccess", true);
				}
				resp.sendRedirect(req.getContextPath() + "/user");
			} else {
				User editUser = userService.findById(id);
				req.setAttribute("message", "Thất bại, vui lòng thử lại!");
				req.setAttribute("editUser", editUser);
				req.setAttribute("roles", roleService.getAllRoles());
				req.setAttribute("isDone", isDone);
				req.setAttribute("isSuccess", false);
				req.getRequestDispatcher("/WEB-INF/views/user-edit.jsp").forward(req, resp);
			}
		}
	}

}
