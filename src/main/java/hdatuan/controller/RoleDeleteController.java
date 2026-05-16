package hdatuan.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hdatuan.service.RoleService;

@WebServlet(name = "roleDeleteServlet", urlPatterns = { "/role-delete" })
public class RoleDeleteController extends HttpServlet {

    private RoleService roleService = new RoleService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idStr = req.getParameter("id");

        if (idStr == null || idStr.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/role");
            return;
        }

        try {
            int roleId = Integer.parseInt(idStr);

            boolean isDeleted = roleService.deleteRole(roleId);

            if (isDeleted) {
                req.setAttribute("deleteMessage", "Xóa quyền thành công!");
                req.setAttribute("isSuccess", true);
            } else {
                req.setAttribute("deleteMessage", "Xóa thất bại hoặc quyền không tồn tại!");
                req.setAttribute("isSuccess", false);
            }

        } catch (NumberFormatException e) {
            req.setAttribute("deleteMessage", "ID quyền không hợp lệ!");
            req.setAttribute("isSuccess", false);
        }

        resp.sendRedirect(req.getContextPath() + "/role");
    }
}
