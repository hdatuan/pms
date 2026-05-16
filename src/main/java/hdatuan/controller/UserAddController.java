package hdatuan.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hdatuan.service.RoleService;
import hdatuan.service.UserService;
import hdatuan.entity.Role;
import hdatuan.entity.User;
import hdatuan.enums.UserRole;

@WebServlet(name="userAddController", urlPatterns= {"/user-add", "/user-edit"})
public class UserAddController extends HttpServlet {
	
	UserService userService = new UserService();
	RoleService roleService = new RoleService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		User user = (User) session.getAttribute("user");
		int roleId = user.getRoleID();
		String servletPath = req.getServletPath();
	
		if ( roleId != UserRole.ADMIN.getId() && (servletPath.equals("/user-edit") || servletPath.equals("/user-add") ) ) {
			resp.sendRedirect(req.getContextPath() + "/404");
			return;
		}
		List<Role> roles = roleService.getAllRoles();
		req.setAttribute("roles", roles);
	    if (servletPath.equals("/user-edit")) {
	        String idStr = req.getParameter("id");
	        // Bug 4 fix: guard khi không có param id
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
	        req.setAttribute("roles", roles);
	        req.getRequestDispatcher("/WEB-INF/views/user-edit.jsp").forward(req, resp);
	        return;
	    }
		
		req.getRequestDispatcher("/WEB-INF/views/user-add.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fullName = req.getParameter("fullname");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		int roleId =  Integer.parseInt(req.getParameter("role-id"));
		boolean isDone = true;
		
        
		// Bug 3 fix: khi edit, password có thể rỗng (giữ nguyên mật khẩu cũ)
		boolean isEditMode = req.getServletPath().equals("/user-edit");
		if (fullName == null || fullName.trim().isEmpty() || email == null || email.trim().isEmpty()) {
			req.setAttribute("message", "Họ tên và Email không được để trống!");
            req.setAttribute("isDone", isDone);
            req.setAttribute("isSuccess", false);
            req.getRequestDispatcher(isEditMode ? "/WEB-INF/views/user-edit.jsp" : "/WEB-INF/views/user-add.jsp").forward(req, resp);
            return;
        }
		if (!isEditMode && (password == null || password.trim().isEmpty())) {
			req.setAttribute("message", "Mật khẩu không được để trống!");
            req.setAttribute("isDone", isDone);
            req.setAttribute("isSuccess", false);
            req.getRequestDispatcher("/WEB-INF/views/user-add.jsp").forward(req, resp);
            return;
        }
		
		if ( req.getServletPath().equals("/user-add") ) {
			
			boolean isSuccess = userService.insertUser(fullName, email, password, roleId);
			
			if (isSuccess) {
				req.setAttribute("message", "Thêm người dùng thành công!");
			} else {
				req.setAttribute("message", "Người dùng đã tồn tại, vui lòng thử lại!");
			}
			req.setAttribute("isDone", isDone);
			req.setAttribute("isSuccess", isSuccess);
			req.getRequestDispatcher("/WEB-INF/views/user-add.jsp").forward(req, resp);			
		} else if ( req.getServletPath().equals("/user-edit") ) {
			int id = Integer.parseInt(req.getParameter("id"));
			// password truyền vào, Service sẽ tự xử lý nếu rỗng (Phương án A)
			boolean isSuccess = userService.updateUser(id, fullName, email, password, roleId);
			// Bug 2 fix: đổi attribute name từ "user" sang "editUser" để khớp với JSP
			User editUser = userService.findById(id);
			List<Role> roles = roleService.getAllRoles();
			if (isSuccess) {
				req.setAttribute("message", "Chỉnh sửa người dùng thành công!");
			} else {
				req.setAttribute("message", "Thất bại, vui lòng thử lại!");
			}
			req.setAttribute("editUser", editUser);
			req.setAttribute("roles", roles);
			req.setAttribute("isDone", isDone);
			req.setAttribute("isSuccess", isSuccess);
			req.getRequestDispatcher("/WEB-INF/views/user-edit.jsp").forward(req, resp);
		}
		
	}
	
}



