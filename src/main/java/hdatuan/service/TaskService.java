package hdatuan.service;

import java.util.List;

import hdatuan.repository.JobRepository;
import hdatuan.repository.TaskRepository;
import hdatuan.repository.UserRepository;
import hdatuan.entity.Job;
import hdatuan.entity.Task;
import hdatuan.entity.User;

public class TaskService {

	private TaskRepository taskRepository = new TaskRepository();
	private JobRepository  jobRepository  = new JobRepository();
	private UserRepository userRepository = new UserRepository();

	public boolean updateTask(Task task) {
		return taskRepository.updateTask(task);
	}

	public Task getTask(int taskId) {
		return taskRepository.findTask(taskId);
	}

	public List<Task> getAllTasks() {
		return taskRepository.findAll();
	}

	public List<Task> getTaskById(int userId) {
		return taskRepository.findByUserId(userId);
	}

	public List<Task> getTaskByUserAndJob(int userId, int jobId) {
		return taskRepository.findByUserAndJob(userId, jobId);
	}

	/**
	 * Thêm mới một tác vụ.
	 * @return true nếu thêm thành công.
	 */
	public boolean addTask(Task task) {
		return taskRepository.insert(task);
	}

	/**
	 * Xóa một tác vụ theo ID.
	 * @return true nếu xóa thành công.
	 */
	public boolean deleteTask(int id) {
		return taskRepository.delete(id);
	}

	/**
	 * Lấy danh sách tất cả dự án — dùng để cung cấp dữ liệu dropdown trong form task.
	 */
	public List<Job> getAllJobs() {
		return jobRepository.findAll();
	}

	/**
	 * Lấy danh sách tất cả users — dùng để cung cấp dữ liệu dropdown trong form task.
	 */
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
}
