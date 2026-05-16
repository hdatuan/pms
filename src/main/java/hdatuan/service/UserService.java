package hdatuan.service;

import java.util.List;

import hdatuan.repository.UserRepository;
import hdatuan.entity.User;

public class UserService {
	
	private UserRepository userRepository = new UserRepository();
	
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	public User findById(int id) {
		return userRepository.findById(id);
	}
	
	public boolean updateUser(int id, String fullName, String email, String password, int roleId) {
		if (password == null || password.trim().isEmpty()) {
			// Không đổi mật khẩu — giữ nguyên mật khẩu cũ trong DB
			return userRepository.updateUserWithoutPassword(id, fullName, email, roleId) > 0;
		}
		return userRepository.updateUser(id, fullName, email, password, roleId) > 0;
	}
	
	public boolean insertUser(String fullName, String email, String password, int roleId) {
		return userRepository.insertUser(fullName, email, password, roleId) > 0;
	}
	
	public void deleteUser(int id) {
		userRepository.deleteUser(id);
	}
}

