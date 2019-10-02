package com.chung.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chung.dao.TodoDao;
import com.chung.dto.TodoDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/toright")
public class TodoTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String json = request.getReader().readLine();
		ObjectMapper mapper = new ObjectMapper();
		TodoDto todoDto = mapper.readValue(json, TodoDto.class);
		
		TodoDao dao = new TodoDao();
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		
		switch(todoDto.getType()) {
		case "TODO":
			todoDto.setType("DOING");
			break;
		case "DOING":
			todoDto.setType("DONE");
			break;
		default:
			todoDto = null;
		}
		if(todoDto == null) {
			out.print("fail");
			out.close();
			return;
		}
		int updateCount = dao.updateTodo(todoDto);
		
		if(updateCount == 1) {
			out.print("success");
		}else {
			out.print("fail");
		}
		
		out.close();
	}

}
