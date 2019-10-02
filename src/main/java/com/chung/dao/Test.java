package com.chung.dao;

import com.chung.dto.TodoDto;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TodoDao dao = new TodoDao();
		TodoDto dto = new TodoDto();
		dto.setTitle("흐흐");
		dto.setName("황충희");
		dto.setSequence(2);
		dao.addTodo(dto);
		
//		TodoDto dto2 = dao.getTodos("TODO").get(0);
//		dto2.setType("DOING");
//		
//		dao.updateTodo(dto2);
//		
//		System.out.println(dao.getTodos("TODO"));
//		System.out.println(dao.getTodos("DOING"));
//		System.out.println(dao.getTodos("DONE"));
	}

}
