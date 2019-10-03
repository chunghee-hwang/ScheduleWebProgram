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

@WebServlet("/type")
public class TodoTypeServlet extends HttpServlet {
	
	//클라이언트에서 AJAX로 스케줄의 type을 바꾸라는 요청이 왔을 경우 처리 함수
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String json = request.getReader().readLine();
		ObjectMapper mapper = new ObjectMapper();
		TodoDto todoDto = mapper.readValue(json, TodoDto.class);

		TodoDao dao = TodoDao.getInstance();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();

		switch (todoDto.getType()) {
		case "TODO":
			todoDto.setType("DOING");
			break;
		case "DOING":
			todoDto.setType("DONE");
			break;
		default:
			todoDto = null;
		}
		if (todoDto == null) {
			out.print("fail");
			out.close();
			return;
		}
		int updateCount = dao.updateTodo(todoDto);

		if (updateCount == 1) {
			out.print("success");
		} else {
			out.print("fail");
		}

		out.close();
	}

}
