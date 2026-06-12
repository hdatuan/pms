package hdatuan.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import hdatuan.service.RoleService;
import hdatuan.entity.Role;

@WebServlet(name = "roleController", urlPatterns = { "/role", "/role-add", "/role-edit", "/role-delete" })
public class RoleController extends HttpServlet {

    private RoleService roleService = new RoleService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String servletPath = req.getServletPath();

        if (servletPath.equals("/role")) {
            // Hiển thị danh sách quyền
            List<Role> roleList = roleService.getAllRoles();
            req.setAttribute("roles", roleList);

            HttpSession session = req.getSession(false);
            if (session != null) {
                Object msg = session.getAttribute("deleteMessage");
                Object success = session.getAttribute("isSuccess");

                if (msg != null) {
                    req.setAttribute("deleteMessage", msg);
                    req.setAttribute("isSuccess", success);

                    session.removeAttribute("deleteMessage");
                    session.removeAttribute("isSuccess");
                }
            }
            req.getRequestDispatcher("/WEB-INF/views/role-table.jsp").forward(req, resp);

        } else if (servletPath.equals("/role-add")) {
            // Hiển thị giao diện thêm mới
            req.setAttribute("isDone", false);
            req.setAttribute("isSuccess", false);
            req.getRequestDispatcher("/WEB-INF/views/role-add.jsp").forward(req, resp);

        } else if (servletPath.equals("/role-edit")) {
            // Hiển thị giao diện chỉnh sửa
            req.setAttribute("isDone", false);
            req.setAttribute("isSuccess", false);

            String idStr = req.getParameter("id");
            if (idStr == null || idStr.isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/role");
                return;
            }

            try {
                int id = Integer.parseInt(idStr);
                Role editRole = roleService.findById(id);

                if (editRole == null) {
                    req.setAttribute("message", "Không tìm thấy quyền!");
                    req.setAttribute("isDone", true);
                    req.setAttribute("isSuccess", false);
                } else {
                    req.setAttribute("editRole", editRole);
                }
            } catch (NumberFormatException e) {
                req.setAttribute("message", "ID quyền không hợp lệ!");
                req.setAttribute("isDone", true);
                req.setAttribute("isSuccess", false);
            }

            req.getRequestDispatcher("/WEB-INF/views/role-edit.jsp").forward(req, resp);

        } else if (servletPath.equals("/role-delete")) {
            // Xử lý xóa quyền
            String idStr = req.getParameter("id");
            HttpSession session = req.getSession();

            if (idStr == null || idStr.isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/role");
                return;
            }

            try {
                int roleId = Integer.parseInt(idStr);
                boolean isDeleted = roleService.deleteRole(roleId);

                if (isDeleted) {
                    session.setAttribute("deleteMessage", "Xóa quyền thành công!");
                    session.setAttribute("isSuccess", true);
                } else {
                    session.setAttribute("deleteMessage", "Xóa thất bại hoặc quyền đang được sử dụng/không tồn tại!");
                    session.setAttribute("isSuccess", false);
                }
            } catch (NumberFormatException e) {
                session.setAttribute("deleteMessage", "ID quyền không hợp lệ!");
                session.setAttribute("isSuccess", false);
            }

            resp.sendRedirect(req.getContextPath() + "/role");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String servletPath = req.getServletPath();
        String roleName = req.getParameter("roleName");
        String roleDescription = req.getParameter("roleDesc");
        String roleIdStr = req.getParameter("roleId");

        boolean isDone = true;

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

            try {
                int roleId = Integer.parseInt(roleIdStr);
                isSuccess = roleService.updateRole(roleId, roleName, roleDescription);
                req.setAttribute("message",
                        isSuccess ? "Cập nhật quyền thành công!" : "Cập nhật thất bại, vui lòng thử lại!");
            } catch (NumberFormatException e) {
                isSuccess = false;
                req.setAttribute("message", "ID quyền không hợp lệ!");
            }
        }

        req.setAttribute("isDone", isDone);
        req.setAttribute("isSuccess", isSuccess);
        forwardByPath(req, resp, servletPath);
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


