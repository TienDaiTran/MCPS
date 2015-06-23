$(document).ready(function() {
	
	$('input').iCheck({
        checkboxClass: 'icheckbox_square-blue',
        radioClass: 'iradio_square-blue',
        increaseArea: '20%' // optional
      });
	
	$("#inputEmail").focus(function() {
		$("#message").html("");
	});
	
	$("#inputPassword").focus(function() {
		$("#message").html("");
	});
	
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
			url : "./adminlogin",
			data : "email=" + email.val() + "&pass=" + password.val(),
			datatype : "text",
			success : function(msg) {
				if (msg == "Exist") {
					window.location.assign("./admin/account");
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