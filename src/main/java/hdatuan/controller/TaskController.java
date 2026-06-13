package hdatuan.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hdatuan.entity.Job;
import hdatuan.entity.Task;
import hdatuan.entity.User;
import hdatuan.enums.UserRole;
import hdatuan.service.TaskService;

/**
 * TaskController — xử lý toàn bộ luồng quản lý Tác vụ.
 *
 * Các URL được xử lý:
 *   GET  /task          — Danh sách tác vụ
 *   GET  /task-add      — Hiển thị form thêm mới
 *   POST /task-add      — Xử lý thêm mới
 *   GET  /task-edit     — Hiển thị form chỉnh sửa
 *   POST /task-edit     — Xử lý cập nhật
 *   GET  /task-delete   — Xóa tác vụ và redirect về danh sách
 */
@WebServlet(name = "taskController",
        urlPatterns = {"/task", "/task-add", "/task-edit", "/task-delete"})
public class TaskController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private TaskService taskService = new TaskService();

	// ─── GET ────────────────────────────────────────────────────────────────
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String path = req.getServletPath();

		switch (path) {
			case "/task":
				handleList(req, resp);
				break;
			case "/task-add":
				handleShowAdd(req, resp);
				break;
			case "/task-edit":
				handleShowEdit(req, resp);
				break;
			case "/task-delete":
				handleDelete(req, resp);
				break;
			default:
				resp.sendRedirect(req.getContextPath() + "/task");
		}
	}

	// ─── POST ───────────────────────────────────────────────────────────────
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String path = req.getServletPath();

		if ("/task-add".equals(path)) {
			handleDoAdd(req, resp);
		} else if ("/task-edit".equals(path)) {
			handleDoEdit(req, resp);
		} else {
			resp.sendRedirect(req.getContextPath() + "/task");
		}
	}

	// ─── Private Handlers ────────────────────────────────────────────────────

	/** Hiển thị danh sách toàn bộ tác vụ */
	private void handleList(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		List<Task> taskList = taskService.getAllTasks();
		req.setAttribute("tasks", taskList);

		// Phân quyền hiển thị nút hành động
		HttpSession session = req.getSession(false);
		if (session != null) {
			User sessionUser = (User) session.getAttribute("user");
			boolean canManage = sessionUser != null
					&& (sessionUser.getRoleID() == UserRole.ADMIN.getId()
					    || sessionUser.getRoleID() == UserRole.MANAGER.getId());
			req.setAttribute("canManage", canManage);

			// Đọc flash message từ session (sau khi redirect)
			Object msg     = session.getAttribute("flashMessage");
			Object success = session.getAttribute("flashSuccess");
			if (msg != null) {
				req.setAttribute("flashMessage", msg);
				req.setAttribute("flashSuccess", success);
				session.removeAttribute("flashMessage");
				session.removeAttribute("flashSuccess");
			}
		}

		req.getRequestDispatcher("/WEB-INF/views/task.jsp").forward(req, resp);
	}

	/** Hiển thị form thêm mới tác vụ */
	private void handleShowAdd(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("jobs",     taskService.getAllJobs());
		req.setAttribute("users",    taskService.getAllUsers());
		req.setAttribute("isDone",   false);
		req.setAttribute("isSuccess", false);
		req.getRequestDispatcher("/WEB-INF/views/task-add.jsp").forward(req, resp);
	}

	/** Hiển thị form chỉnh sửa tác vụ */
	private void handleShowEdit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String idStr = req.getParameter("id");
		if (idStr == null || idStr.isEmpty()) {
			resp.sendRedirect(req.getContextPath() + "/task");
			return;
		}
		try {
			int id = Integer.parseInt(idStr);
			Task task = taskService.getTask(id);
			if (task == null || task.getId() == 0) {
				resp.sendRedirect(req.getContextPath() + "/task");
				return;
			}
			req.setAttribute("editTask",  task);
			req.setAttribute("jobs",      taskService.getAllJobs());
			req.setAttribute("users",     taskService.getAllUsers());
			req.setAttribute("isDone",    false);
			req.setAttribute("isSuccess", false);
		} catch (NumberFormatException e) {
			resp.sendRedirect(req.getContextPath() + "/task");
			return;
		}
		req.getRequestDispatcher("/WEB-INF/views/task-edit.jsp").forward(req, resp);
	}

	/** Xử lý xóa tác vụ và redirect về danh sách */
	private void handleDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String idStr = req.getParameter("id");
		HttpSession session = req.getSession();

		if (idStr == null || idStr.isEmpty()) {
			resp.sendRedirect(req.getContextPath() + "/task");
			return;
		}
		try {
			int id = Integer.parseInt(idStr);
			boolean deleted = taskService.deleteTask(id);
			if (deleted) {
				session.setAttribute("flashMessage", "Xóa tác vụ thành công!");
				session.setAttribute("flashSuccess", true);
			} else {
				session.setAttribute("flashMessage", "Xóa thất bại hoặc tác vụ không tồn tại!");
				session.setAttribute("flashSuccess", false);
			}
		} catch (NumberFormatException e) {
			session.setAttribute("flashMessage", "ID tác vụ không hợp lệ!");
			session.setAttribute("flashSuccess", false);
		}
		resp.sendRedirect(req.getContextPath() + "/task");
	}

	/** Xử lý POST thêm mới tác vụ */
	private void handleDoAdd(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String name       = req.getParameter("name");
		String jobIdStr   = req.getParameter("job_id");
		String userIdStr  = req.getParameter("user_id");
		String statusStr  = req.getParameter("status_id");
		String startStr   = req.getParameter("start_date");
		String endStr     = req.getParameter("end_date");

		// Validation cơ bản
		if (name == null || name.trim().isEmpty()
				|| jobIdStr == null || jobIdStr.isEmpty()
				|| userIdStr == null || userIdStr.isEmpty()
				|| statusStr == null || statusStr.isEmpty()) {
			req.setAttribute("message",   "Vui lòng điền đầy đủ thông tin tác vụ!");
			req.setAttribute("isDone",    true);
			req.setAttribute("isSuccess", false);
			req.setAttribute("jobs",      taskService.getAllJobs());
			req.setAttribute("users",     taskService.getAllUsers());
			req.getRequestDispatcher("/WEB-INF/views/task-add.jsp").forward(req, resp);
			return;
		}

		try {
			int jobId   = Integer.parseInt(jobIdStr);
			int userId  = Integer.parseInt(userIdStr);
			int statusId = Integer.parseInt(statusStr);

			java.sql.Date startDate = (startStr != null && !startStr.isEmpty())
					? java.sql.Date.valueOf(startStr) : null;
			java.sql.Date endDate   = (endStr != null && !endStr.isEmpty())
					? java.sql.Date.valueOf(endStr) : null;

			if (startDate != null && endDate != null && endDate.before(startDate)) {
				req.setAttribute("message",   "Ngày kết thúc phải sau ngày bắt đầu!");
				req.setAttribute("isDone",    true);
				req.setAttribute("isSuccess", false);
				req.setAttribute("jobs",      taskService.getAllJobs());
				req.setAttribute("users",     taskService.getAllUsers());
				req.getRequestDispatcher("/WEB-INF/views/task-add.jsp").forward(req, resp);
				return;
			}

			Task task = new Task();
			task.setName(name.trim());
			task.setJob_id(jobId);
			task.setUser_id(userId);
			task.setStatus_id(statusId);
			task.setStart_date(startDate);
			task.setEnd_date(endDate);

			boolean success = taskService.addTask(task);
			if (success) {
				HttpSession session = req.getSession();
				session.setAttribute("flashMessage", "Thêm tác vụ thành công!");
				session.setAttribute("flashSuccess", true);
				resp.sendRedirect(req.getContextPath() + "/task");
			} else {
				req.setAttribute("message",   "Thêm thất bại, vui lòng thử lại!");
				req.setAttribute("isDone",    true);
				req.setAttribute("isSuccess", false);
				req.setAttribute("jobs",      taskService.getAllJobs());
				req.setAttribute("users",     taskService.getAllUsers());
				req.getRequestDispatcher("/WEB-INF/views/task-add.jsp").forward(req, resp);
			}

		} catch (IllegalArgumentException e) {
			req.setAttribute("message",   "Định dạng ngày không hợp lệ (YYYY-MM-DD)!");
			req.setAttribute("isDone",    true);
			req.setAttribute("isSuccess", false);
			req.setAttribute("jobs",      taskService.getAllJobs());
			req.setAttribute("users",     taskService.getAllUsers());
			req.getRequestDispatcher("/WEB-INF/views/task-add.jsp").forward(req, resp);
		}
	}

	/** Xử lý POST cập nhật tác vụ */
	private void handleDoEdit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String idStr      = req.getParameter("id");
		String name       = req.getParameter("name");
		String jobIdStr   = req.getParameter("job_id");
		String userIdStr  = req.getParameter("user_id");
		String statusStr  = req.getParameter("status_id");
		String startStr   = req.getParameter("start_date");
		String endStr     = req.getParameter("end_date");

		if (idStr == null || idStr.isEmpty()) {
			resp.sendRedirect(req.getContextPath() + "/task");
			return;
		}

		int id = 0;
		Task task = null;
		try {
			id = Integer.parseInt(idStr);
			task = taskService.getTask(id);
		} catch (NumberFormatException e) {
			resp.sendRedirect(req.getContextPath() + "/task");
			return;
		}

		if (task == null || task.getId() == 0) {
			resp.sendRedirect(req.getContextPath() + "/task");
			return;
		}

		if (name == null || name.trim().isEmpty()
				|| jobIdStr == null || jobIdStr.isEmpty()
				|| userIdStr == null || userIdStr.isEmpty()
				|| statusStr == null || statusStr.isEmpty()) {
			req.setAttribute("message",   "Vui lòng điền đầy đủ thông tin tác vụ!");
			req.setAttribute("isDone",    true);
			req.setAttribute("isSuccess", false);
			req.setAttribute("editTask",  task);
			req.setAttribute("jobs",      taskService.getAllJobs());
			req.setAttribute("users",     taskService.getAllUsers());
			req.getRequestDispatcher("/WEB-INF/views/task-edit.jsp").forward(req, resp);
			return;
		}

		try {
			int jobId    = Integer.parseInt(jobIdStr);
			int userId   = Integer.parseInt(userIdStr);
			int statusId = Integer.parseInt(statusStr);

			java.sql.Date startDate = (startStr != null && !startStr.isEmpty())
					? java.sql.Date.valueOf(startStr) : null;
			java.sql.Date endDate   = (endStr != null && !endStr.isEmpty())
					? java.sql.Date.valueOf(endStr) : null;

			if (startDate != null && endDate != null && endDate.before(startDate)) {
				req.setAttribute("message",   "Ngày kết thúc phải sau ngày bắt đầu!");
				req.setAttribute("isDone",    true);
				req.setAttribute("isSuccess", false);
				req.setAttribute("editTask",  task);
				req.setAttribute("jobs",      taskService.getAllJobs());
				req.setAttribute("users",     taskService.getAllUsers());
				req.getRequestDispatcher("/WEB-INF/views/task-edit.jsp").forward(req, resp);
				return;
			}

			task.setName(name.trim());
			task.setJob_id(jobId);
			task.setUser_id(userId);
			task.setStatus_id(statusId);
			task.setStart_date(startDate);
			task.setEnd_date(endDate);

			boolean success = taskService.updateTask(task);
			if (success) {
				HttpSession session = req.getSession();
				session.setAttribute("flashMessage", "Cập nhật tác vụ thành công!");
				session.setAttribute("flashSuccess", true);
				resp.sendRedirect(req.getContextPath() + "/task");
			} else {
				req.setAttribute("message",   "Cập nhật thất bại, vui lòng thử lại!");
				req.setAttribute("isDone",    true);
				req.setAttribute("isSuccess", false);
				req.setAttribute("editTask",  task);
				req.setAttribute("jobs",      taskService.getAllJobs());
				req.setAttribute("users",     taskService.getAllUsers());
				req.getRequestDispatcher("/WEB-INF/views/task-edit.jsp").forward(req, resp);
			}

		} catch (IllegalArgumentException e) {
			req.setAttribute("message",   "Định dạng ngày không hợp lệ (YYYY-MM-DD)!");
			req.setAttribute("isDone",    true);
			req.setAttribute("isSuccess", false);
			req.setAttribute("editTask",  task);
			req.setAttribute("jobs",      taskService.getAllJobs());
			req.setAttribute("users",     taskService.getAllUsers());
			req.getRequestDispatcher("/WEB-INF/views/task-edit.jsp").forward(req, resp);
		}
	}
}
