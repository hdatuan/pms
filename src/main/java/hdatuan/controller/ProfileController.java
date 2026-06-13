package hdatuan.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hdatuan.service.JobService;
import hdatuan.service.TaskService;
import hdatuan.entity.Job;
import hdatuan.entity.Task;
import hdatuan.entity.User;

@WebServlet(name = "profileController", urlPatterns = { "/profile", "/profile-edit" })
public class ProfileController extends HttpServlet {

	private JobService jobService = new JobService();
	private TaskService taskService = new TaskService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String servletPath = req.getServletPath();
		HttpSession session = req.getSession(false);
		User user = session != null ? (User) session.getAttribute("user") : null;

		if (user == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		if (servletPath.equals("/profile-edit")) {
			List<Job> jobList = jobService.getJobById(user.getId());
			req.setAttribute("jobList", jobList);

			String jobIdParam = req.getParameter("jobId");
			if (jobIdParam != null && !jobIdParam.isEmpty()) {
				int jobId = Integer.parseInt(jobIdParam);
				List<Task> taskList = taskService.getTaskByUserAndJob(user.getId(), jobId);
				req.setAttribute("taskList", taskList);
			}

			String taskIdParam = req.getParameter("taskId");
			if (taskIdParam != null && !taskIdParam.isEmpty()) {
				int taskId = Integer.parseInt(taskIdParam);
				Task task = taskService.getTask(taskId);

				// Kiểm tra IDOR: chỉ cho phép xem task của chính mình
				if (task == null || task.getUser_id() != user.getId()) {
					resp.sendRedirect(req.getContextPath() + "/403");
					return;
				}

				req.setAttribute("task", task);
			} else {
				req.setAttribute("task", null);
			}

			req.getRequestDispatcher("/WEB-INF/views/profile-edit.jsp").forward(req, resp);
			return;
		}

		// Mặc định là route /profile
		List<Task> tasks = taskService.getTaskById(user.getId());
		req.setAttribute("tasks", tasks);

		int totalTask = tasks.size();
		int notStartedTask = 0;
		int inProgressTask = 0;
		int doneTask = 0;

		for (Task task : tasks) {
			String status = task.getStatus_name();
			if ("Chưa thực hiện".equals(status))
				notStartedTask++;
			else if ("Đang thực hiện".equals(status))
				inProgressTask++;
			else if ("Đã thực hiện".equals(status))
				doneTask++;
		}

		double notStartedPercent = totalTask > 0 ? (notStartedTask * 100.0 / totalTask) : 0;
		double inProgressPercent = totalTask > 0 ? (inProgressTask * 100.0 / totalTask) : 0;
		double donePercent = totalTask > 0 ? (doneTask * 100.0 / totalTask) : 0;

		req.setAttribute("totalTask", totalTask);
		req.setAttribute("notStartedTask", notStartedTask);
		req.setAttribute("inProgressTask", inProgressTask);
		req.setAttribute("doneTask", doneTask);
		req.setAttribute("notStartedPercent", notStartedPercent);
		req.setAttribute("inProgressPercent", inProgressPercent);
		req.setAttribute("donePercent", donePercent);
		req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String servletPath = req.getServletPath();

		if (servletPath.equals("/profile-edit")) {
			HttpSession session = req.getSession(false);
			User sessionUser = session != null ? (User) session.getAttribute("user") : null;

			if (sessionUser == null) {
				resp.sendRedirect(req.getContextPath() + "/login");
				return;
			}

			String jobIdParam = req.getParameter("jobId");
			String taskIdParam = req.getParameter("taskId");

			if (taskIdParam != null && !taskIdParam.isEmpty()) {
				Task task = taskService.getTask(Integer.parseInt(taskIdParam));

				// Kiểm tra IDOR: từ chối nếu task không thuộc về người dùng đang đăng nhập
				if (task == null || task.getUser_id() != sessionUser.getId()) {
					resp.sendRedirect(req.getContextPath() + "/403");
					return;
				}

				String startDateStr = req.getParameter("start_date");
				String endDateStr = req.getParameter("end_date");

				if (startDateStr != null && !startDateStr.trim().isEmpty()) {
					java.sql.Date startDate = java.sql.Date.valueOf(startDateStr);
					task.setStart_date(startDate);
				} else {
					resp.sendRedirect(req.getContextPath() + "/profile-edit?jobId=" + jobIdParam + "&taskId="
							+ taskIdParam + "&message=fail");
					return;
				}

				if (endDateStr != null && !endDateStr.trim().isEmpty()) {
					task.setEnd_date(java.sql.Date.valueOf(endDateStr));
				} else {
					resp.sendRedirect(req.getContextPath() + "/profile-edit?jobId=" + jobIdParam + "&taskId="
							+ taskIdParam + "&message=fail");
					return;
				}

				task.setStatus_id(Integer.parseInt(req.getParameter("status_id")));

				boolean isUpdated = taskService.updateTask(task);

				if (isUpdated) {
					resp.sendRedirect(req.getContextPath() + "/profile-edit?jobId=" + jobIdParam + "&taskId="
							+ taskIdParam + "&message=success");
				} else {
					resp.sendRedirect(req.getContextPath() + "/profile-edit?jobId=" + jobIdParam + "&taskId="
							+ taskIdParam + "&message=fail");
				}
			} else {
				resp.sendRedirect(req.getContextPath() + "/profile-edit?jobId=" + jobIdParam + "&taskId="
						+ taskIdParam + "&message=fail");
			}
		}
	}
}
