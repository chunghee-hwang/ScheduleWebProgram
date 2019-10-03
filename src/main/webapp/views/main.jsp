<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- 사용자가 이 페이지에 MainServlet에서 포워딩을 통해서 들어오지 않았다면 MainServlet으로 이동시킴 -->
<c:if test="${requestScope.plans == null}">
	<c:redirect url="/main" />
</c:if>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>나의 해야할 일들</title>
	<link rel="stylesheet" type="text/css" href="/todo/css/common.css">
	<link rel="stylesheet" type="text/css" href="/todo/css/main.css">
</head>

<body>

	<div class="new_todo_button">
		<a class="new_todo_anchor link" href="/todo/scheduler">새로운 TODO 등록</a>
	</div>

	<h2 class="rotated_text">나의 해야할 일들</h2>

	<div class="plan_table">
		<c:forEach var="list" items="${requestScope.plans}" varStatus="status">
			<c:set var="index" value="${status.index}" />
			<div class="list_view">
				<div class="card_head_container">
					<h1 class="card_head_type">${requestScope.types[index]}</h1>
				</div>
				<c:forEach var="entry" items="${list}">
					<c:set var="regDate" value="${entry.regDate}" />
					<div class="card_entry_container">
						<h2 class="card_entry_title">${entry.title}</h2>
						<div class="card_entry_content">
							등록날짜: <span class="reg_date">${fn:substring(regDate,0,19) }</span>, ${entry.name}, 우선순위 ${entry.sequence}
						</div>
						<c:if test="${entry.type ne 'DONE'}">
							<a class="to_right_button" href="javascript:void(0);"
								onclick='onClickRightButton(this, "${entry.id}")'> 
								<img class="button_image" src="imgs/right_arrow.png" alt="오른쪽으로 이동">
							</a>
						</c:if>
					</div>
				</c:forEach>
			</div>
		</c:forEach>
	</div>

	<script type="text/javascript" src="/todo/js/main.js"></script>
	
</body>

</html>