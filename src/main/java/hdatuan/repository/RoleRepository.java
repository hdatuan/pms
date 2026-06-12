package hdatuan.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import hdatuan.config.MySQLConfig;
import hdatuan.entity.Role;

public class RoleRepository {
	
	public int deleteRole(int roleId) {
	    int row = 0;
	    String checkQuery = "SELECT COUNT(*) AS count FROM users WHERE role_id = ?";
	    String deleteQuery = "DELETE FROM roles WHERE id = ?";

	    try (
	        Connection connection = MySQLConfig.getConnection();
	        PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
	    ) {
	        checkStmt.setInt(1, roleId);
	        try (ResultSet rs = checkStmt.executeQuery()) {
	            if (rs.next() && rs.getInt("count") > 0) {
	                System.out.println("Không thể xóa role: đang có user sử dụng!");
	                return 0;
	            }
	        }

	        try (PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)) {
	            deleteStmt.setInt(1, roleId);
	            row = deleteStmt.executeUpdate();
	        }
	    } catch (Exception e) {
	        System.out.println("Error: " + e.getMessage());
	    }

	    return row;
	}


	
	public int updateRole(int roleId, String roleName, String description) {
		int row = 0;
		String query = "UPDATE roles SET name = ?, description = ? WHERE id = ?";
		
		try ( 	Connection connection = MySQLConfig.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);
		) {
			statement.setString(1, roleName);
			statement.setString(2, description);
			statement.setInt(3, roleId);
			row = statement.executeUpdate();
			
		} catch ( Exception e ) {
			System.out.println(e.getMessage());
		}
		
		return row;
	}

	public List<Role> findAll() {
		List<Role> roleList = new ArrayList<>();
		String query = "SELECT * FROM roles";
		
		try (
			Connection connection = MySQLConfig.getConnection();
		) {
			if ( connection == null ) {
				throw new RuntimeException("Database Disconnected");
			}
			try (
				PreparedStatement statement = connection.prepareStatement(query);
				ResultSet resultSet = statement.executeQuery();
			) {
				while ( resultSet.next() ) {
					Role role = new Role();
					role.setId(resultSet.getInt("id"));
					role.setName(resultSet.getString("name"));
					role.setDescription(resultSet.getString("description"));
					roleList.add(role);
				}
			}
		} catch ( Exception e ) {
			System.out.println("Error " + e.getMessage());
		}
		
		return roleList;
	}
	
	public int insertRole(String name, String description ) {
		int rowCount = 0;
		String query = "INSERT INTO roles( name, description ) VALUES (? , ?)";
		
		try (
			Connection connection = MySQLConfig.getConnection();
		) {
			if ( connection == null ) {
				throw new RuntimeException("Database disconnected");
			}
			
			// Check if the role is existed
			List<Role> insertedRole = this.findAll();
			for(Role role : insertedRole) {
				if ( role.getName().equals(name) ) return 0;
			}
			
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, name);
				statement.setString(2, description);
				rowCount = statement.executeUpdate();
			}
		} catch ( Exception e ) {
			System.out.println("Error : " + e.getMessage());
		}
		
		return rowCount;
	}
	
	public Role findById(int id) {
	    Role role = null;
	    String query = "SELECT * FROM roles WHERE id = ?";
	    try (
	        Connection connection = MySQLConfig.getConnection();
	        PreparedStatement statement = connection.prepareStatement(query);
	    ) {
	        statement.setInt(1, id);
	        try (ResultSet rs = statement.executeQuery()) {
	            if (rs.next()) {
	                role = new Role();
	                role.setId(rs.getInt("id"));
	                role.setName(rs.getString("name"));
	                role.setDescription(rs.getString("description"));
	            }
	        }
	    } catch (Exception e) {
	        System.out.println("Error: " + e.getMessage());
	    }
	    return role;
	}

}

