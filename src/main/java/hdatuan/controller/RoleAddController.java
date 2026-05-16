package hdatuan.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import hdatuan.service.RoleService;
import hdatuan.entity.Role;
import hdatuan.entity.User;
import hdatuan.enums.UserRole;

@WebServlet(name = "roleAddEditServlet", urlPatterns = { "/role-add", "/role-edit" })
public class RoleAddController extends HttpServlet {

    private RoleService roleService = new RoleService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String roleName = req.getParameter("roleName");
        String roleDescription = req.getParameter("roleDesc");
        String roleIdStr = req.getParameter("roleId");

        boolean isDone = true;
        String servletPath = req.getServletPath();

        if (roleName == null || roleName.trim().isEmpty()) {
            req.setAttribute("message", "Tên quyền không được để trống!");
            req.setAttribute("isDone", isDone);
            req.setAttribute("isSuccess", false);
            forwardByPath(req, resp, servletPath);
            return;
        }

        boolean isSuccess;

        if (servletPath.equals("/role-add")) {
            isSuccess = roleService.insertRole(roleName, roleDescription);
            req.setAttribute("message", isSuccess ? "Thêm quyền thành công!" : "Quyền đã tồn tại hoặc thêm thất bại!");
        } else {
            if (roleIdStr == null || roleIdStr.isEmpty()) {
                req.setAttribute("message", "Thiếu ID quyền cần chỉnh sửa!");
                req.setAttribute("isDone", isDone);
                req.setAttribute("isSuccess", false);
                req.getRequestDispatcher("/WEB-INF/views/role-edit.jsp").forward(req, resp);
                return;
            }

            int roleId = Integer.parseInt(roleIdStr);
            isSuccess = roleService.updateRole(roleId, roleName, roleDescription);
            req.setAttribute("message",
                    isSuccess ? "Cập nhật quyền thành công!" : "Cập nhật thất bại, vui lòng thử lại!");
        }

        req.setAttribute("isDone", isDone);
        req.setAttribute("isSuccess", isSuccess);
        forwardByPath(req, resp, servletPath);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        User user = (User) (session != null ? session.getAttribute("user") : null);

        if (user == null || user.getRoleID() != UserRole.ADMIN.getId()) {
            resp.sendRedirect(req.getContextPath() + "/403");
            return;
        }

        req.setAttribute("isDone", false);
        req.setAttribute("isSuccess", false);

        String servletPath = req.getServletPath();

        if (servletPath.equals("/role-add")) {
            req.getRequestDispatcher("/WEB-INF/views/role-add.jsp").forward(req, resp);
        } else if (servletPath.equals("/role-edit")) {
            String idStr = req.getParameter("id");
            if (idStr == null || idStr.isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/role");
                return;
            }

            int id = Integer.parseInt(idStr);
            Role editRole = roleService.findById(id);

            if (editRole == null) {
                req.setAttribute("message", "Không tìm thấy quyền!");
                req.setAttribute("isDone", true);
                req.setAttribute("isSuccess", false);
            } else {
                req.setAttribute("editRole", editRole);
            }

            req.getRequestDispatcher("/WEB-INF/views/role-edit.jsp").forward(req, resp);
        }
    }

    private void forwardByPath(HttpServletRequest req, HttpServletResponse resp, String path)
            throws ServletException, IOException {
        if (path.equals("/role-add")) {
            req.getRequestDispatcher("/WEB-INF/views/role-add.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/views/role-edit.jsp").forward(req, resp);
        }
    }
}
