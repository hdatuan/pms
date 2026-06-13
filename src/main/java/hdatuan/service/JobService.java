package hdatuan.service;

import java.util.List;

import hdatuan.repository.JobRepository;
import hdatuan.repository.TaskRepository;
import hdatuan.entity.Job;
import hdatuan.entity.Task;

public class JobService {
	private JobRepository jobRepository = new JobRepository();
	private TaskRepository taskRepository = new TaskRepository();
	
	public List<Job> getAllJobs() {
		return jobRepository.findAll();
	}
	
	public List<Job> getJobById(int user_id){
		return jobRepository.findById(user_id);
	}

	/**
	 * Tìm thông tin chi tiết một dự án theo ID.
	 */
	public Job findJobById(int id) {
		return jobRepository.findJobById(id);
	}

	/**
	 * Thêm mới một dự án.
	 * @return true nếu thêm thành công.
	 */
	public boolean addJob(Job job) {
		return jobRepository.insert(job) > 0;
	}

	/**
	 * Cập nhật thông tin một dự án.
	 * @return true nếu cập nhật thành công.
	 */
	public boolean updateJob(Job job) {
		return jobRepository.update(job) > 0;
	}

	/**
	 * Xóa dự án và toàn bộ task liên quan (cascade).
	 * @return true nếu xóa thành công.
	 */
	public boolean deleteJob(int id) {
		return jobRepository.delete(id) > 0;
	}

	/**
	 * Lấy danh sách tất cả tác vụ của một dự án để hiển thị trang chi tiết tiến độ.
	 */
	public List<Task> getTasksByJobId(int jobId) {
		return taskRepository.findByJobId(jobId);
	}
}
