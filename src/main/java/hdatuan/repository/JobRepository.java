package hdatuan.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import hdatuan.config.MySQLConfig;
import hdatuan.entity.Job;


public class JobRepository {
	
	public List<Job> findById(int user_id) {
		List<Job> jobList = new ArrayList<Job>();
		String query = "SELECT DISTINCT j.*\r\n"
				+ "FROM users u\r\n"
				+ "JOIN tasks t\r\n"
				+ "ON u.id = t.user_id \r\n"
				+ "JOIN jobs j\r\n"
				+ "ON j.id = t.job_id\r\n"
				+ "WHERE u.id = ?";
		try (
				Connection connection = MySQLConfig.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);
		) {
			statement.setInt(1, user_id);
			
			try ( ResultSet resultSet = statement.executeQuery()) {
				while( resultSet.next() ) {
					Job job = new Job();
					job.setId(resultSet.getInt("id"));
					job.setName(resultSet.getString("name"));
					job.setStart_date(resultSet.getDate("start_date"));
					job.setEnd_date(resultSet.getDate("end_date"));
					jobList.add(job);
				}
				return jobList;
			} 
		}
		catch ( Exception e ) {
			System.out.println(e);
		}
		return null;
		
	}
	
	public List<Job> findAll() {
		List<Job> jobList = new ArrayList<>();
		String query = "SELECT * FROM jobs";
		
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
				while( resultSet.next() ) {
					Job job = new Job();
					job.setId(resultSet.getInt("id"));
					job.setName(resultSet.getString("name"));
					job.setStart_date(resultSet.getDate("start_date"));
					job.setEnd_date(resultSet.getDate("end_date"));
					
					jobList.add(job);
				}
			}
		} catch ( Exception e ) {
			System.out.println("Error " + e.getMessage());
		}
		
		return jobList;
	}
}

