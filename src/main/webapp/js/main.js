//main.jsp를 위한 javascript

//카드의 엔트리들을 등록일시 순으로 정렬하기위한 Compare Function
function compareEntries(node1, node2) {
	var reg_date = node1.querySelector(".reg_date").innerText.split(" ").join("T");
	var reg_date2 = node2.querySelector(".reg_date").innerText.split(" ").join("T");
	reg_date = Date.parse(reg_date);
	reg_date2 = Date.parse(reg_date2);
	return reg_date - reg_date2;
}

// 카드의 엔트리들을 등록일시 오름차순으로 정렬하는 함수
function sortCardByRegisterDate(list_view) {
	var children = list_view.children;
	var nodeList = [];
	for (var i = 1; i < children.length; i++) {
		nodeList.push(children[i]);
	}

	for (var i = 0; i < nodeList.length; i++) {
		list_view.removeChild(nodeList[i]);
	}
	nodeList.sort(this.compareEntries);
	for (var i = 0; i < nodeList.length; i++) {
		list_view.appendChild(nodeList[i]);
	}
}

// 오른족 화살표 버튼을 눌렀을 때의 리스너들을 등록하는 함수
function addRightButtonClickListener() {
	var to_right_buttons = document.querySelectorAll(".to_right_button");
	Array.prototype.forEach.call(to_right_buttons, function(button) {
		var schedule_id = button.parentElement.querySelector(".schedule_id").innerText;
		var schedule_type_elem = button.parentElement.querySelector(".schedule_type")
		button.addEventListener("click", function() {
			this.onClickRightButton(button, schedule_id, schedule_type_elem);
		}.bind(this));
	});
}

// 엔트리 안에있는 오른쪽 화살표를 눌렀을 경우 처리되는 내용을 담은 함수
function onClickRightButton(button, schedule_id, schedule_type_elem) {
	var schedule_type = schedule_type_elem.innerText;
	var data = 
	{
		"id" : schedule_id,
		"type" : schedule_type
	};
	var thisObject = this;
	this.requestAjax("PUT", "/todo/type", function() {
		if (this.status == 200 && this.responseText.trim() == "success") {
			thisObject.moveCardToNext(button, schedule_type_elem);
		} else {
			alert("상태 변경을 실패하였습니다.");
		}
	}, data);
}

// DOM을 조작하여 카드를 오른쪽으로 옮기는 함수
function moveCardToNext(button, schedule_type_elem){
	var list_views = document.querySelectorAll(".list_view");
	var schedule_type = schedule_type_elem.innerText;
	var card_entry_container = button.parentElement;
	
	switch (schedule_type) {
	case "TODO":
		list_views[1].appendChild(card_entry_container);
		this.sortCardByRegisterDate(list_views[1]);
		schedule_type_elem.innerText = "DOING";
		break;
	case "DOING":
		list_views[2].appendChild(card_entry_container);
		this.sortCardByRegisterDate(list_views[2]);
		button.style.display = "none";
		schedule_type_elem.innerText = "DONE";
		break;
	default:
		alert("상태 변경을 실패하였습니다.");
	}
}


// 서버에 AJAX 요청을 하는 함수
// method : GET, POST, PUT...
// url: Servlet 경로
// loadListener: AJAX 요청 응답을 받았을 때 호출될 콜백함수
// object : post 또는 PUT 방식으로 보낼 경우 전송될 객체
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

window.addEventListener("DOMContentLoaded", function() {
	this.addRightButtonClickListener();
});
