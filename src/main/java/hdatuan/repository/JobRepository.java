package hdatuan.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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

	public Job findJobById(int id) {
		String query = "SELECT * FROM jobs WHERE id = ?";
		try (
			Connection connection = MySQLConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
		) {
			statement.setInt(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					Job job = new Job();
					job.setId(resultSet.getInt("id"));
					job.setName(resultSet.getString("name"));
					job.setStart_date(resultSet.getDate("start_date"));
					job.setEnd_date(resultSet.getDate("end_date"));
					return job;
				}
			}
		} catch (Exception e) {
			System.out.println("Error " + e.getMessage());
		}
		return null;
	}

	public int insert(Job job) {
		String query = "INSERT INTO jobs (name, start_date, end_date) VALUES (?, ?, ?)";
		try (
			Connection connection = MySQLConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		) {
			statement.setString(1, job.getName());
			statement.setDate(2, job.getStart_date());
			statement.setDate(3, job.getEnd_date());
			return statement.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error " + e.getMessage());
		}
		return 0;
	}

	public int update(Job job) {
		String query = "UPDATE jobs SET name = ?, start_date = ?, end_date = ? WHERE id = ?";
		try (
			Connection connection = MySQLConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
		) {
			statement.setString(1, job.getName());
			statement.setDate(2, job.getStart_date());
			statement.setDate(3, job.getEnd_date());
			statement.setInt(4, job.getId());
			return statement.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error " + e.getMessage());
		}
		return 0;
	}

	public int delete(int id) {
		String queryDeleteTasks = "DELETE FROM tasks WHERE job_id = ?";
		String queryDeleteJob = "DELETE FROM jobs WHERE id = ?";
		try (
			Connection connection = MySQLConfig.getConnection();
		) {
			connection.setAutoCommit(false);
			try (
				PreparedStatement psTasks = connection.prepareStatement(queryDeleteTasks);
				PreparedStatement psJob = connection.prepareStatement(queryDeleteJob);
			) {
				psTasks.setInt(1, id);
				psTasks.executeUpdate();
				
				psJob.setInt(1, id);
				int result = psJob.executeUpdate();
				connection.commit();
				return result;
			} catch (Exception e) {
				connection.rollback();
				throw e;
			}
		} catch (Exception e) {
			System.out.println("Error " + e.getMessage());
		}
		return 0;
	}
}
