$(document).ready(function(){
	
	///////////////////CREATE ACCOUNT////////////////////////
	
	$("#inputRetypePassword").change(function(){
		hide($("#passwordMatchLabel"));
		hide($("#passwordNotMatchLabel"));
		var password = $("#inputPassword").val();
		var retypePassword = $("#inputRetypePassword").val();
		if (password != retypePassword) {
			show($("#passwordNotMatchLabel"));
		} else {
			show($("#passwordMatchLabel"));
		}
	});
	
	$("#inputPassword").change(function(){
		hide($("#passwordMatchLabel"));
		hide($("#passwordNotMatchLabel"));
		var password = $("#inputPassword").val();
		var retypePassword = $("#inputRetypePassword").val();
		if (password != retypePassword) {
			show($("#passwordNotMatchLabel"));
		} else {
			show($("#passwordMatchLabel"));
		}
	});
	
	$("#birthdayDatetimepicker").change(function(){
		hide($("#birthdayNotReal"));
	});
	
	// check form create account submit
	$("#accountInfoForm").submit(function(e){
		e.preventDefault();
		var result = false;
		// check day
		$(".mydatetime").each(function(){
			var hiddenParent = $(this).parents(".hidden");
			if (hiddenParent == null) {
				// not check datetime in hidden region
				result = true;
				return;
			}
			var date = $(this).val();
			var f = moment(date, "DD/MM/YYYY").toDate();
			var now = new Date()
			if (f < now) {
				result = true;
			} else {
				show($(this).parents("div.form-group").find("label.has-error"));
			}
		});
		// check match password
		var password = $("#inputPassword").val();
		var retypePassword = $("#inputRetypePassword").val();
		if (password != retypePassword) {
			result = false;
		}
		if (result == true) {
	    $.post($(this).attr("action"), $(this).serialize(), function(msg){
	    	if ("success" == msg) {
				$("#toast").contents()[2].nodeValue = "Cập nhật tài khoản thành công";
				$("#toast").fadeIn();
				setTimeout(function(){
				       $("#toast").fadeOut();
				},1000);
			} else {
				$("#toast").contents()[2].nodeValue = "Tài khoản không tồn tại";
				$("#toast").fadeIn();
				setTimeout(function(){
				       $("#toast").fadeOut();
				},1000);
			}
	    });
		}
		return false;
	  });
	/////////////////////////////////////////////////////////
});