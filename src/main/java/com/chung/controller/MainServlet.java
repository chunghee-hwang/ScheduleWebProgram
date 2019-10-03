package com.chung.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");

		TodoDao dao = TodoDao.getInstance();
		List<TodoDto> todos = dao.getTodos("TODO");
		List<TodoDto> doings = dao.getTodos("DOING");
		List<TodoDto> dones = dao.getTodos("DONE");
		List<List<TodoDto>> plans = new ArrayList<>(Arrays.asList(todos, doings, dones));

		request.setAttribute("plans", plans);
		request.setAttribute("types", new ArrayList<>(Arrays.asList("TODO", "DOING", "DONE")));
		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/main.jsp");
		dispatcher.forward(request, response);
	}

}
