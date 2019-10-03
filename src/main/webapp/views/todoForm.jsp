<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>할일 등록</title>
<link rel="stylesheet" type="text/css" href="/todo/css/common.css">
<link rel="stylesheet" type="text/css" href="/todo/css/todoForm.css">
</head>

<body>
	<h1 class="title">할일 등록</h1>
	<form id="addtodoform" action="/todo/schedule" method="POST"
		accept-charset="utf-8">

		<div class="input_container">
			<label class="question_label">어떤일인가요?</label><br> <br> 
			<input class="title_input" type="text" name="title" placeholder="운동하기(24자까지)" maxlength="24" required>
		</div>

		<div class="input_container">
			<label class="question_label">누가 할일인가요?</label><br> <br> 
			<input class="name_input" type="text" name="name" placeholder="홍길동" maxlength="10" required>
		</div>

		<div class="input_container">
			<label class="question_label">우선순위를 선택하세요</label><br> <br>
			<label class="seq_input"><input type="radio" name="seq" value="1" required>1순위</label> 
			<label class="seq_input"><input type="radio" name="seq" value="2">2순위</label> 
			<label class="seq_input"><input type="radio" name="seq" value="3">3순위</label>
		</div>

		<div class="input_container">
			<button class="previous_button button">이전</button>
			<input class="button space" type="submit" value="제출"> 
			<input class="button" type="reset" value="내용 지우기">
		</div>

	</form>

	<script type="text/javascript" src="/todo/js/todoForm.js"></script>
</body>

</html>