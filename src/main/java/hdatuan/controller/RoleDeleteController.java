package hdatuan.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hdatuan.service.RoleService;
import hdatuan.entity.User;
import hdatuan.enums.UserRole;


@WebServlet(name = "roleDeleteServlet", urlPatterns = {"/role-delete"})
public class RoleDeleteController extends HttpServlet {

    private RoleService roleService = new RoleService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        if (user == null || user.getRoleID() != UserRole.ADMIN.getId()) {
        	resp.sendRedirect(req.getContextPath() + "/403");
            return;
        }

        String idStr = req.getParameter("id");

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
                session.setAttribute("deleteMessage", "Xóa thất bại hoặc quyền không tồn tại!");
                session.setAttribute("isSuccess", false);
            }

        } catch (NumberFormatException e) {
            session.setAttribute("deleteMessage", "ID quyền không hợp lệ!");
            session.setAttribute("isSuccess", false);
        }

        resp.sendRedirect(req.getContextPath() + "/role");
    }
}

