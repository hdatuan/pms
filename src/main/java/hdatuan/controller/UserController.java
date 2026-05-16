package hdatuan.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hdatuan.service.UserService;
import hdatuan.entity.User;
import hdatuan.enums.UserRole;


@WebServlet( name = "userController", urlPatterns = {"/user", "/user-delete"})
public class UserController extends HttpServlet {
	
	UserService userService = new UserService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(false);
		User sessionUser = session != null ? (User) session.getAttribute("user") : null;

		if ( req.getServletPath().equals("/user-delete") ) {
			if ( sessionUser == null || sessionUser.getRoleID() != UserRole.ADMIN.getId() ) {
				resp.sendRedirect(req.getContextPath() + "/404");
				return;
			}
			int user_id = Integer.parseInt(req.getParameter("id"));
			userService.deleteUser(user_id);
			resp.sendRedirect(req.getContextPath() + "/user");
			return;
		}

		List<User> userList = userService.getAllUsers();
		boolean isAdmin = sessionUser != null && sessionUser.getRoleID() == UserRole.ADMIN.getId();

		req.setAttribute("users", userList);
		req.setAttribute("isAdmin", isAdmin);

		req.getRequestDispatcher("/WEB-INF/views/user-table.jsp").forward(req, resp);
	}
	
	
	
}



