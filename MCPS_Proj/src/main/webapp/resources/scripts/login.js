$(document).ready(function() {
	

	
	$("#inputEmail").keypress(function(event){
		if (event.keyCode == 13) {
			$("#btnLogin").click();
		}
	});
	
	$("#inputPassword").keypress(function(event){
		if (event.keyCode == 13) {
			$("#btnLogin").click();
		}
	});
	
	$("#btnLogin").click(function(){
		var email = $("#inputEmail");
		var password = $("#inputPassword");
		$.ajax({
			type : "POST",
			url : "./login",
			data : "email=" + email.val() + "&password=" + password.val(),
			datatype : "text",
			success : function(msg) {
				if (msg == "Exist") {
					window.location.assign("./");
				} else {
					$("#message").html(msg);
				}
			},
			error : function (e) {
				alert(e);
			}
		});
	});
});