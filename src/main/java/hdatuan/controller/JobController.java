package hdatuan.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hdatuan.entity.Job;
import hdatuan.entity.Task;
import hdatuan.service.JobService;

/**
 * JobController — xử lý toàn bộ luồng quản lý Dự án / Nhóm công việc.
 * 
 * Các URL được xử lý:
 *   GET  /groupwork          — Danh sách dự án
 *   GET  /groupwork-add      — Hiển thị form thêm mới
 *   POST /groupwork-add      — Xử lý thêm mới
 *   GET  /groupwork-edit     — Hiển thị form chỉnh sửa
 *   POST /groupwork-edit     — Xử lý cập nhật
 *   GET  /groupwork-delete   — Xóa dự án (cascade tasks) và redirect
 *   GET  /groupwork-details  — Xem chi tiết tiến độ dự án
 */
@WebServlet(name = "jobController",
        urlPatterns = {"/groupwork", "/groupwork-add", "/groupwork-edit",
                       "/groupwork-delete", "/groupwork-details"})
public class JobController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private JobService jobService = new JobService();

	// ─── GET ────────────────────────────────────────────────────────────────
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String path = req.getServletPath();

		switch (path) {

			case "/groupwork":
				handleList(req, resp);
				break;

			case "/groupwork-add":
				handleShowAdd(req, resp);
				break;

			case "/groupwork-edit":
				handleShowEdit(req, resp);
				break;

			case "/groupwork-delete":
				handleDelete(req, resp);
				break;

			case "/groupwork-details":
				handleDetails(req, resp);
				break;

			default:
				resp.sendRedirect(req.getContextPath() + "/groupwork");
		}
	}

	// ─── POST ───────────────────────────────────────────────────────────────
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String path = req.getServletPath();

		if ("/groupwork-add".equals(path)) {
			handleDoAdd(req, resp);
		} else if ("/groupwork-edit".equals(path)) {
			handleDoEdit(req, resp);
		} else {
			resp.sendRedirect(req.getContextPath() + "/groupwork");
		}
	}

	// ─── Private Handlers ────────────────────────────────────────────────────

	/** Hiển thị danh sách toàn bộ dự án */
	private void handleList(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		List<Job> jobList = jobService.getAllJobs();
		req.setAttribute("jobs", jobList);

		// Đọc flash message từ session (sau khi redirect từ delete)
		HttpSession session = req.getSession(false);
		if (session != null) {
			Object msg     = session.getAttribute("flashMessage");
			Object success = session.getAttribute("flashSuccess");
			if (msg != null) {
				req.setAttribute("flashMessage", msg);
				req.setAttribute("flashSuccess", success);
				session.removeAttribute("flashMessage");
				session.removeAttribute("flashSuccess");
			}
		}

		req.getRequestDispatcher("/WEB-INF/views/groupwork.jsp").forward(req, resp);
	}

	/** Hiển thị form thêm mới dự án */
	private void handleShowAdd(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("isDone", false);
		req.setAttribute("isSuccess", false);
		req.getRequestDispatcher("/WEB-INF/views/groupwork-add.jsp").forward(req, resp);
	}

	/** Hiển thị form chỉnh sửa dự án */
	private void handleShowEdit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String idStr = req.getParameter("id");
		if (idStr == null || idStr.isEmpty()) {
			resp.sendRedirect(req.getContextPath() + "/groupwork");
			return;
		}
		try {
			int id = Integer.parseInt(idStr);
			Job job = jobService.findJobById(id);
			if (job == null) {
				resp.sendRedirect(req.getContextPath() + "/groupwork");
				return;
			}
			req.setAttribute("editJob", job);
			req.setAttribute("isDone", false);
			req.setAttribute("isSuccess", false);
		} catch (NumberFormatException e) {
			resp.sendRedirect(req.getContextPath() + "/groupwork");
			return;
		}
		req.getRequestDispatcher("/WEB-INF/views/groupwork-edit.jsp").forward(req, resp);
	}

	/** Xử lý xóa dự án và redirect về danh sách */
	private void handleDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String idStr = req.getParameter("id");
		HttpSession session = req.getSession();

		if (idStr == null || idStr.isEmpty()) {
			resp.sendRedirect(req.getContextPath() + "/groupwork");
			return;
		}
		try {
			int id = Integer.parseInt(idStr);
			boolean deleted = jobService.deleteJob(id);
			if (deleted) {
				session.setAttribute("flashMessage", "Xóa dự án thành công!");
				session.setAttribute("flashSuccess", true);
			} else {
				session.setAttribute("flashMessage", "Xóa thất bại hoặc dự án không tồn tại!");
				session.setAttribute("flashSuccess", false);
			}
		} catch (NumberFormatException e) {
			session.setAttribute("flashMessage", "ID dự án không hợp lệ!");
			session.setAttribute("flashSuccess", false);
		}
		resp.sendRedirect(req.getContextPath() + "/groupwork");
	}

	/**
	 * Hiển thị chi tiết tiến độ dự án:
	 *  - Thống kê % Chưa thực hiện / Đang thực hiện / Hoàn thành
	 *  - Danh sách công việc nhóm theo tên nhân viên
	 */
	private void handleDetails(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String idStr = req.getParameter("id");
		if (idStr == null || idStr.isEmpty()) {
			resp.sendRedirect(req.getContextPath() + "/groupwork");
			return;
		}
		try {
			int id = Integer.parseInt(idStr);
			Job job = jobService.findJobById(id);
			if (job == null) {
				resp.sendRedirect(req.getContextPath() + "/groupwork");
				return;
			}

			List<Task> tasks = jobService.getTasksByJobId(id);
			int total      = tasks.size();
			int notStarted = 0, inProgress = 0, done = 0;

			// Nhóm công việc theo tên nhân viên
			Map<String, List<Task>> tasksByUser = new LinkedHashMap<>();
			for (Task t : tasks) {
				if (t.getStatus_id() == 1) notStarted++;
				else if (t.getStatus_id() == 2) inProgress++;
				else if (t.getStatus_id() == 3) done++;

				tasksByUser.computeIfAbsent(t.getUser_name(), k -> new ArrayList<>()).add(t);
			}

			// Tính % (tránh chia cho 0)
			int pctNotStarted = total > 0 ? Math.round(100f * notStarted / total) : 0;
			int pctInProgress = total > 0 ? Math.round(100f * inProgress / total) : 0;
			int pctDone       = total > 0 ? Math.round(100f * done / total) : 0;

			req.setAttribute("job",           job);
			req.setAttribute("tasksByUser",   tasksByUser);
			req.setAttribute("total",         total);
			req.setAttribute("notStarted",    notStarted);
			req.setAttribute("inProgress",    inProgress);
			req.setAttribute("done",          done);
			req.setAttribute("pctNotStarted", pctNotStarted);
			req.setAttribute("pctInProgress", pctInProgress);
			req.setAttribute("pctDone",       pctDone);

		} catch (NumberFormatException e) {
			resp.sendRedirect(req.getContextPath() + "/groupwork");
			return;
		}
		req.getRequestDispatcher("/WEB-INF/views/groupwork-details.jsp").forward(req, resp);
	}

	/** Xử lý POST thêm mới dự án */
	private void handleDoAdd(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String name        = req.getParameter("name");
		String startDateStr = req.getParameter("start_date");
		String endDateStr   = req.getParameter("end_date");

		if (name == null || name.trim().isEmpty()
				|| startDateStr == null || startDateStr.isEmpty()
				|| endDateStr == null || endDateStr.isEmpty()) {
			req.setAttribute("message", "Vui lòng điền đầy đủ thông tin dự án!");
			req.setAttribute("isDone", true);
			req.setAttribute("isSuccess", false);
			req.getRequestDispatcher("/WEB-INF/views/groupwork-add.jsp").forward(req, resp);
			return;
		}

		try {
			java.sql.Date startDate = java.sql.Date.valueOf(startDateStr);
			java.sql.Date endDate   = java.sql.Date.valueOf(endDateStr);

			if (endDate.before(startDate)) {
				req.setAttribute("message", "Ngày kết thúc phải sau ngày bắt đầu!");
				req.setAttribute("isDone", true);
				req.setAttribute("isSuccess", false);
				req.getRequestDispatcher("/WEB-INF/views/groupwork-add.jsp").forward(req, resp);
				return;
			}

			Job job = new Job();
			job.setName(name.trim());
			job.setStart_date(startDate);
			job.setEnd_date(endDate);

			boolean success = jobService.addJob(job);
			req.setAttribute("message",   success ? "Thêm dự án thành công!" : "Thêm thất bại, vui lòng thử lại!");
			req.setAttribute("isDone",    true);
			req.setAttribute("isSuccess", success);

		} catch (IllegalArgumentException e) {
			req.setAttribute("message", "Định dạng ngày không hợp lệ (YYYY-MM-DD)!");
			req.setAttribute("isDone",    true);
			req.setAttribute("isSuccess", false);
		}

		req.getRequestDispatcher("/WEB-INF/views/groupwork-add.jsp").forward(req, resp);
	}

	/** Xử lý POST cập nhật dự án */
	private void handleDoEdit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String idStr       = req.getParameter("id");
		String name        = req.getParameter("name");
		String startDateStr = req.getParameter("start_date");
		String endDateStr   = req.getParameter("end_date");

		if (idStr == null || idStr.isEmpty()) {
			resp.sendRedirect(req.getContextPath() + "/groupwork");
			return;
		}

		try {
			int id = Integer.parseInt(idStr);
			Job job = jobService.findJobById(id);

			if (job == null) {
				resp.sendRedirect(req.getContextPath() + "/groupwork");
				return;
			}

			if (name == null || name.trim().isEmpty()
					|| startDateStr == null || startDateStr.isEmpty()
					|| endDateStr == null || endDateStr.isEmpty()) {
				req.setAttribute("message", "Vui lòng điền đầy đủ thông tin dự án!");
				req.setAttribute("isDone", true);
				req.setAttribute("isSuccess", false);
				req.setAttribute("editJob", job);
				req.getRequestDispatcher("/WEB-INF/views/groupwork-edit.jsp").forward(req, resp);
				return;
			}

			java.sql.Date startDate = java.sql.Date.valueOf(startDateStr);
			java.sql.Date endDate   = java.sql.Date.valueOf(endDateStr);

			if (endDate.before(startDate)) {
				req.setAttribute("message", "Ngày kết thúc phải sau ngày bắt đầu!");
				req.setAttribute("isDone", true);
				req.setAttribute("isSuccess", false);
				req.setAttribute("editJob", job);
				req.getRequestDispatcher("/WEB-INF/views/groupwork-edit.jsp").forward(req, resp);
				return;
			}

			job.setName(name.trim());
			job.setStart_date(startDate);
			job.setEnd_date(endDate);

			boolean success = jobService.updateJob(job);
			req.setAttribute("message",   success ? "Cập nhật dự án thành công!" : "Cập nhật thất bại, vui lòng thử lại!");
			req.setAttribute("isDone",    true);
			req.setAttribute("isSuccess", success);
			req.setAttribute("editJob",   job);

		} catch (IllegalArgumentException e) {
			req.setAttribute("message",   "Dữ liệu không hợp lệ!");
			req.setAttribute("isDone",    true);
			req.setAttribute("isSuccess", false);
		}

		req.getRequestDispatcher("/WEB-INF/views/groupwork-edit.jsp").forward(req, resp);
	}
}
