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

@WebServlet(name="profileEditController", urlPatterns= {"/profile-edit"})
public class ProfileEditController extends HttpServlet {

	private JobService jobService = new JobService();
	private TaskService taskService = new TaskService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		User user = (User) session.getAttribute("user");
		
		List<Job> jobList = jobService.getJobById(user.getId());
		req.setAttribute("jobList", jobList);
		
		String jobIdParam = req.getParameter("jobId");
		if ( jobIdParam != null && !jobIdParam.isEmpty())
		{
			int jobId = Integer.parseInt(jobIdParam);
			List<Task> taskList = taskService.getTaskByUserAndJob(user.getId(), jobId);
			req.setAttribute("taskList", taskList);
		} 
		
		String taskIdParam = req.getParameter("taskId");
		if ( taskIdParam != null && !taskIdParam.isEmpty())
		{
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
	}
	
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession(false);
		User sessionUser = (User) session.getAttribute("user");

		String jobIdParam = req.getParameter("jobId");
		String taskIdParam = req.getParameter("taskId");
		boolean isUpdated = false;
		
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
	            resp.sendRedirect(req.getContextPath() + "/profile-edit?jobId=" + jobIdParam + "&taskId=" + taskIdParam + "&message=fail");
	            return;
			}

			if (endDateStr != null && !endDateStr.trim().isEmpty()) {
			    task.setEnd_date(java.sql.Date.valueOf(endDateStr));
			} else {
	            resp.sendRedirect(req.getContextPath() + "/profile-edit?jobId=" + jobIdParam + "&taskId=" + taskIdParam + "&message=fail");
	            return;
			}

			task.setStatus_id(Integer.parseInt(req.getParameter("status_id")));
			
			isUpdated = taskService.updateTask(task);
			
			if (isUpdated) {
	            resp.sendRedirect(req.getContextPath() + "/profile-edit?jobId=" + jobIdParam + "&taskId=" + taskIdParam + "&message=success");
	        } else {
	            resp.sendRedirect(req.getContextPath() + "/profile-edit?jobId=" + jobIdParam + "&taskId=" + taskIdParam + "&message=fail");
	        }
		} 
		else {
			resp.sendRedirect(req.getContextPath() + "/profile-edit?jobId=" + jobIdParam + "&taskId=" + taskIdParam + "&message=fail");
	    }
	}
	
	
}
