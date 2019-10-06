package com.chung.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.chung.dto.TodoDto;

public class TodoDao {
	private final static String DB_URL = "jdbc:mysql://localhost:3306/tododb?useSSL=false";
	private final static String DB_USER = "connectuser";
	private final static String DB_PASSWORD = "connect123!@#";
	private static TodoDao instance;

	public static TodoDao getInstance() {
		if (instance == null) {
			instance = new TodoDao();
		}
		return instance;
	}

	private TodoDao() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public int addTodo(TodoDto todoDto) {
		int insertCount = 0;
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement ps = conn.prepareStatement(TodoDaoSqls.ADD_TODO);) {
			ps.setString(1, todoDto.getTitle());
			ps.setString(2, todoDto.getName());
			ps.setInt(3, todoDto.getSequence());

			insertCount = ps.executeUpdate();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return insertCount;
	}

	public List<TodoDto> getTodos() {
		List<TodoDto> list = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement ps = conn.prepareStatement(TodoDaoSqls.GET_TODOS);
				ResultSet rs = ps.executeQuery();) {
			while (rs.next()) {
				TodoDto dto = new TodoDto();
				dto.setId(rs.getLong(1));
				dto.setTitle(rs.getString(2));
				dto.setName(rs.getString(3));
				dto.setSequence(rs.getInt(4));
				dto.setType(rs.getString(5));
				dto.setRegDate(rs.getString(6));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public int updateTodo(TodoDto todoDto) {
		int updateCount = 0;
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement ps = conn.prepareStatement(TodoDaoSqls.UPDATE_TODO);) {
			ps.setString(1, todoDto.getType());
			ps.setLong(2, todoDto.getId());
			updateCount = ps.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return updateCount;
	}
}