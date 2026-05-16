package hdatuan.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hdatuan.entity.User;
import hdatuan.enums.UserRole;

/**
 * AuthorizationFilter — chạy SAU AuthenticationFilter.
 * Chịu trách nhiệm duy nhất: kiểm tra quyền hạn (Authorization).
 * Thứ tự chạy được đảm bảo bởi khai báo tường minh trong web.xml.
 */
@WebFilter(filterName = "authorizationFilter", urlPatterns = {"/*"})
public class AuthorizationFilter implements Filter {


    private static final Set<String> ADMIN_ONLY_PATHS = new HashSet<>(Arrays.asList(
            "/user-add",
            "/user-edit",
            "/user-delete",
            "/role-add",
            "/role-edit",
            "/role-delete"
    ));

    private static final Set<String> ADMIN_AND_MANAGER_PATHS = new HashSet<>(Arrays.asList(

            "/task-add",
            "/task-edit",
            "/groupwork-add",
            "/groupwork-edit"
    ));

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String contextPath = req.getContextPath();
        String uri = req.getRequestURI();
        String path = (uri.length() > contextPath.length())
                ? uri.substring(contextPath.length())
                : "/";

        // Bỏ qua kiểm tra quyền nếu là public path hoặc file tĩnh
        if (isPublicPath(path)) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            chain.doFilter(request, response);
            return;
        }

        int roleId = user.getRoleID();

        if (roleId != UserRole.ADMIN.getId()){
            if (ADMIN_ONLY_PATHS.contains(path)){
                resp.sendRedirect(contextPath + "/403");
                return;
            }

            if (roleId != UserRole.MANAGER.getId()) {
                if (ADMIN_AND_MANAGER_PATHS.contains(path)) {
                    resp.sendRedirect(contextPath + "/403");
                }
            }
        }

        chain.doFilter(request, response);
    }

    private boolean isPublicPath(String path) {
        return path.startsWith("/login")
                || path.startsWith("/css")
                || path.startsWith("/js")
                || path.startsWith("/images")
                || path.startsWith("/bootstrap")
                || path.startsWith("/plugins")
                || path.startsWith("/less")
                || path.endsWith(".css")
                || path.endsWith(".js")
                || path.endsWith(".png")
                || path.endsWith(".jpg")
                || path.endsWith(".jpeg")
                || path.endsWith(".gif")
                || path.endsWith(".ico")
                || path.endsWith(".woff")
                || path.endsWith(".woff2")
                || path.equals("/404")
                || path.equals("/403");
    }
}
