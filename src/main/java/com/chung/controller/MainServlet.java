package com.chung.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chung.dao.TodoDao;
import com.chung.dto.TodoDto;

@WebServlet("/main")
public class MainServlet extends HttpServlet {

	//main.jsp에 필요 정보를 담아서 포워딩해주는 함수
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String TODO = "TODO";
		final String DOING = "DOING";
		final String DONE = "DONE";
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");

		TodoDao dao = TodoDao.getInstance();
		
		List<TodoDto> list = dao.getTodos();
		List<TodoDto> todos = list.stream().filter(dto-> dto.getType().equals(TODO)).collect(Collectors.toList());
		List<TodoDto> doings = list.stream().filter(dto-> dto.getType().equals(DOING)).collect(Collectors.toList());
		List<TodoDto> dones = list.stream().filter(dto-> dto.getType().equals(DONE)).collect(Collectors.toList());
		
		List<List<TodoDto>> plans = new ArrayList<>(Arrays.asList(todos, doings, dones));

		request.setAttribute("plans", plans);
		request.setAttribute("types", new ArrayList<>(Arrays.asList(TODO, DOING, DONE)));
		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/main.jsp");
		dispatcher.forward(request, response);
	}

}
