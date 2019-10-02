<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<style>
		body {
			padding: 1%;
			width: 100%;
			height: 100%;
			text-align: center;
		}

		.rotated_text {
			margin: 0;
			position: absolute;
			top: 100px;
			left: 50px;
			transform: rotate(-30deg);
			color: rgba(0, 47, 255, 0.568);
		}

		.link {
			text-decoration: none;
			font-weight: bold;
		}

		.new_todo_button {
			display: table;
			position: absolute;
			text-align: center;
			right: 50px;
			top: 100px;
			position: absolute;
		}

		.new_todo_anchor {
			display: table-cell;
			width: 200px;
			height: 50px;
			color: white;
			background-color: rgba(0, 102, 255, 0.514);
			vertical-align: middle;
		}

		.plan_table {
			margin-top: 150px;
			text-align: center;
		}


		.plan_table>.list_view {

			display: inline-block;
			vertical-align: top;
			margin: 5px;
		}

		.card_head_container {
			display: table;
			width: 400px;
			height: 80px;
			text-align: left;
			background-color: rgba(0, 102, 255, 0.514);
		}

		.card_head_type {
			display: table-cell;
			vertical-align: middle;
			padding: 10px;
			color: white;
		}

		.card_entry_container {
			position: relative;
			width: 400px;
			height: 150px;
			text-align: justify;
			background-color: rgba(146, 190, 255, 0.514);
			margin-top: 10px;
		}

		/*글자 너비 초과 시 줄임 표시 : https://webdir.tistory.com/483 */
		.card_entry_title,
		.card_entry_content {
			overflow: hidden;
			text-overflow: ellipsis;
			white-space: nowrap;
			margin: 0;
			padding: 10px;
		}

		.to_right_button {
			margin: 0;
			position: absolute;
			right: 5px;
		}

		.button_image {
			width: 40px;
			height: 40px;
		}
	</style>
</head>

<body>

	<div class="new_todo_button">
		<a class="new_todo_anchor link" href="#">새로운 TODO 등록</a>
	</div>

	<h2 class="rotated_text">나의 해야할 일들</h2>

	<div class="plan_table">
		<c:forEach var="list" items="${plans}" varStatus="status">
			<c:set var="index" value="${status.index}" />
			<div class="list_view">
				<div class="card_head_container">
					<h1 class="card_head_type">${types[index]}</h1>
				</div>
				<c:forEach var="entry" items="${list}">
					<c:set var="regDate" value="${entry.regDate}" />
					<div class="card_entry_container">
						<h2 class="card_entry_title">${entry.title}</h2>
						<div class="card_entry_content">등록날짜: <span class="reg_date">${fn:substring(regDate,0,19) }</span>, ${entry.name}, 우선순위 ${entry.sequence}
						</div>
						<c:if test="${entry.type ne 'DONE'}">
							<a class="to_right_button" href="javascript:void(0);" onclick='onClickRightButton(this, "${entry.id}")'>
								<img class="button_image" src="imgs/right_arrow.png" alt="오른쪽으로 이동">
							</a>
						</c:if>
					</div>
				</c:forEach>
			</div>
		</c:forEach>
	</div>

	<script type="text/javascript">

		function requestAjax(method, url, loadListener, object) {
			var oReq = new XMLHttpRequest();
			oReq.addEventListener("load", loadListener);
			oReq.open(method, url);
			if (object != null && object != undefined) {
				oReq.setRequestHeader("Content-Type", "application/json");
				var data = JSON.stringify(object);
				oReq.send(data);
			}
			else {
				oReq.send();
			}
		}


		function compareEntries(node1, node2){
			var reg_date = node1.querySelector(".reg_date").innerText.split(" ").join("T");
			var reg_date2 = node2.querySelector(".reg_date").innerText.split(" ").join("T");
			reg_date = Date.parse(reg_date);
			reg_date2 = Date.parse(reg_date2);
			return reg_date - reg_date2;
		}

		function sortCardByRegisterDate(list_view)
		{
			var children = list_view.children;
			var nodeList = [];
			for(var i = 1; i < children.length; i++){
				nodeList.push(children[i]);
			}
			console.log(nodeList);
			for(var i = 0; i < nodeList.length; i++){
				list_view.removeChild(nodeList[i]);
			}
			nodeList.sort(compareEntries);
			for(var i = 0; i < nodeList.length; i++){
				list_view.appendChild(nodeList[i]);
			}
		}

		function onClickRightButton(button, id) {
			var card_entry_container = button.parentElement;
			var card_head_container = card_entry_container.parentElement.children[0];
			var list_views = document.querySelectorAll(".list_view");
			var type = card_head_container.querySelector(".card_head_type").innerText;

			requestAjax("PUT", "/todo/toright", function () {
				if (this.status == 200 && this.responseText.trim() == "success") {

					switch (type) {
						case "TODO":
							list_views[1].appendChild(card_entry_container);
							sortCardByRegisterDate(list_views[1]);
							break;
						case "DOING":
							list_views[2].appendChild(card_entry_container);
							sortCardByRegisterDate(list_views[2]);
							button.style.display="none";
							break;
					}
				}
				else {
					alert("상태 변경을 실패하였습니다.")
				}

			}, { "id":id, "type":type });
		}

		window.addEventListener("load", function () {
			var plans = "${plans}";
			if(!plans){
				alert("잘못된 접근입니다.");
				window.location.href="/todo";
			}
		});

	</script>

</body>

</html>