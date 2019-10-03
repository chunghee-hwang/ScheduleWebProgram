package com.chung.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.function.Function;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chung.dao.TodoDao;
import com.chung.dto.TodoDto;

@WebServlet("/schedule")
public class TodoAddServlet extends HttpServlet {

	//클라이언트에게 메시지를 뿌리는 함수
	private void sendMessage(HttpServletResponse response, String msg) throws IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print("<script>alert('" + msg + "');</script>");
		out.close();
	}

	//새로운 스케줄을 등록 요청했을 시 처리 함수
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		TodoDao dao = TodoDao.getInstance();
		TodoDto dto = new TodoDto();
		request.setCharacterEncoding("utf-8");
		String title = request.getParameter("title");
		String name = request.getParameter("name");
		String seq = request.getParameter("seq");

		if (title == null || title.trim().equals("") || name == null || name.trim().equals("") || seq == null
				|| seq.trim().equals("")) {
			response.sendRedirect("/todo/scheduler");
			return;
		}

		dto.setTitle(title);
		dto.setName(name);
		dto.setSequence(Integer.parseInt(seq));
		int insertCount = dao.addTodo(dto);

		if (insertCount != 1) {
			sendMessage(response, "스케줄 입력을 실패하였습니다.");
		} else {
			response.sendRedirect("/todo/main");
		}

	}

}
