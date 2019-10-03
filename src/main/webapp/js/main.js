//main.jsp를 위한 javascript

//서버에 AJAX 요청을 하는 함수 
//method : GET, POST, PUT...
//url: Servlet 경로
//loadListener: AJAX 요청 응답을 받았을 때 호출될 콜백함수
//object : post 또는 PUT 방식으로 보낼 경우 전송될 객체
function requestAjax(method, url, loadListener, object) {
	var oReq = new XMLHttpRequest();
	oReq.addEventListener("load", loadListener);
	oReq.open(method, url);
	if (object != null && object != undefined) {
		oReq.setRequestHeader("Content-Type", "application/json");
		var data = JSON.stringify(object);
		oReq.send(data);
	} else {
		oReq.send();
	}
}

//카드의 엔트리들을 등록일시 순으로 정렬하기위한 Compare Function
function compareEntries(node1, node2) {
	var reg_date = node1.querySelector(".reg_date").innerText.split(" ")
			.join("T");
	var reg_date2 = node2.querySelector(".reg_date").innerText.split(" ").join(
			"T");
	reg_date = Date.parse(reg_date);
	reg_date2 = Date.parse(reg_date2);
	return reg_date - reg_date2;
}

//카드의 엔트리들을 등록일시 오름차순으로 정렬하는 함수
function sortCardByRegisterDate(list_view) {
	var children = list_view.children;
	var nodeList = [];
	for (var i = 1; i < children.length; i++) {
		nodeList.push(children[i]);
	}
	console.log(nodeList);
	for (var i = 0; i < nodeList.length; i++) {
		list_view.removeChild(nodeList[i]);
	}
	nodeList.sort(compareEntries);
	for (var i = 0; i < nodeList.length; i++) {
		list_view.appendChild(nodeList[i]);
	}
}

//엔트리 안에있는 오른쪽 화살표를 눌렀을 경우 호출되는 함수
function onClickRightButton(button, id) {
	var card_entry_container = button.parentElement;
	var card_head_container = card_entry_container.parentElement.children[0];
	var list_views = document.querySelectorAll(".list_view");
	var type = card_head_container.querySelector(".card_head_type").innerText;

	requestAjax("PUT", "/todo/type", function() {
		if (this.status == 200 && this.responseText.trim() == "success") {
			switch (type) {
			case "TODO":
				list_views[1].appendChild(card_entry_container);
				sortCardByRegisterDate(list_views[1]);
				break;
			case "DOING":
				list_views[2].appendChild(card_entry_container);
				sortCardByRegisterDate(list_views[2]);
				button.style.display = "none";
				break;
			}
		} else {
			alert("상태 변경을 실패하였습니다.")
		}

	}, {
		"id" : id,
		"type" : type
	});
}