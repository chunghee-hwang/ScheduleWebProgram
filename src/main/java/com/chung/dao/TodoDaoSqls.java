package com.chung.dao;

public class TodoDaoSqls {
	public static final String ADD_TODO = 
			"insert into todo(title, name, sequence) values(?, ?, ?)";
	
	public static final String GET_TODOS= 
			"select id, title, name, sequence, type, regdate from todo "
			+ "where type = ? order by regdate asc";
	
	public static final String UPDATE_TODO = 
			"update todo set type = ? where id = ?";
}
