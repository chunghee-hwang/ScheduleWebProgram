//todoForm.jsp를 위한 javascript

window.addEventListener("DOMContentLoaded", function() {
	// 뒤로가기 버튼이 눌렸을 경우
	document.querySelector(".previous_button").addEventListener("click",
			function() {
				window.location.href = "/todo/main";
			});
});