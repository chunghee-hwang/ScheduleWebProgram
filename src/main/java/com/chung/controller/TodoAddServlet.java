package com.chung.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.function.Function;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.chung.dao.TodoDao;
import com.chung.dto.TodoDto;

@WebServlet("/schedule")
public class TodoAddServlet extends HttpServlet {


	//클라이언트에게 메시지를 뿌리는 함수
	private void sendErrorMessage(HttpServletResponse response, String msg) throws IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print("<script>alert('" + msg + "');window.location.href='/todo/scheduler'</script>");
		out.close();
	}

	//새로운 스케줄을 등록 요청했을 시 처리 함수
	//StringUtils Labrary : https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/StringUtils.html
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		final int maxTitleLength = 24;
		
		TodoDao dao = TodoDao.getInstance();
		TodoDto dto = new TodoDto();
		request.setCharacterEncoding("utf-8");
		String title = request.getParameter("title");
		String name = request.getParameter("name");
		String seq = request.getParameter("seq");
		if(StringUtils.isBlank(title) || StringUtils.isBlank(name) || StringUtils.isBlank(seq) || title.length() > maxTitleLength)
		{
			sendErrorMessage(response, "입력 값에 문제가 있습니다.");
			return;
		}

		dto.setTitle(title);
		dto.setName(name);
		dto.setSequence(Integer.parseInt(seq));
		int insertCount = dao.addTodo(dto);

		if (insertCount != 1) {
			sendErrorMessage(response, "스케줄 입력을 실패하였습니다.");
			return;
		}
		response.sendRedirect("/todo/main");
		
	}

}
