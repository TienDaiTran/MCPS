function showDetailInfoRegion(chosenRole) {
	if ("Partner" == chosenRole) {
		$("#detailInfoDiv").html($("#partnerInfo").html());
	} else 	if ("Reviewer" == chosenRole) {
		$("#detailInfoDiv").html($("#reviewerInfo").html());
	} else {
		$("#detailInfoDiv").html("");
	}
	activeDatePicker();
	setDatePickerEndDate($('#datetimepickerFoundingDay'));
	setDatePickerEndDate($('#datetimepickerBirthday'));
}

$(document).ready(function(){
	
	setDatePickerEndDate($('#datetimepickerFoundingDay'));
	setDatePickerEndDate($('#datetimepickerBirthday'));
	
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
				show($("#birthdayNotReal"));
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
				$("#toast").contents()[2].nodeValue = "Tạo tài khoản thành công";
				$("#toast").fadeIn();
				setTimeout(function(){
				       $("#toast").fadeOut(function(){
				    	   window.location.assign(document.location.host + "/mcps/admin/account/create");
				       });
				},1000);
			} else {
				$("#toast").contents()[2].nodeValue = "Email đã tồn tại";
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
	
	
	// click search button funtion
	$("#btnSearch").click(function(){
		var accountId = $("#inputID").val();
		var email = $("#inputEmail").val();
		var roleId = $("#inputRole").val();
		$.post(window.location, {id : accountId, email : email, role : roleId}, function(msg, status) {
			if ("success" == status) {
				$("#listAccountDiv").html(msg);
				$("#example1").dataTable();
			}
		});
	});
	
	// click block account
	$("#listAccountDiv").on("click", ".block-anchor", function(){
		var accountId = $(this).parent().siblings("td.id-account").text();
		$.post(window.location, {id : accountId, block : "true"}, function(msg) {
			if ("success" == msg) {
				$("#toast").contents()[2].nodeValue = "block success";
				$("#toast").fadeIn();
				setTimeout(function(){
				       $("#toast").fadeOut(function(){
				    	   window.location.assign(window.location);
				       });
				},1000);
			}
		});
	});
	
	// click unblock account
	$("#listAccountDiv").on("click", ".unblock-anchor", function(){
		var accountId = $(this).parent().siblings("td.id-account").text();
		$.post(window.location, {id : accountId, block : "false"}, function(msg) {
			if ("success" == msg) {
				$("#toast").contents()[2].nodeValue = "unblock success";
				$("#toast").fadeIn();
				setTimeout(function(){
				       $("#toast").fadeOut(function(){
				    	   window.location.assign(window.location);
				       });
				},1000);
			}
		});
	});
	
	// click delete account
	$("#listAccountDiv").on("click", ".delete-anchor", function(){
		var accountId = $(this).parent().siblings("td.id-account").text();
		$.post("delete", {id : accountId}, function(msg) {
			if ("success" == msg) {
				$("#toast").contents()[2].nodeValue = "delete success";
				$("#toast").fadeIn();
				setTimeout(function(){
				       $("#toast").fadeOut(function(){
				    	   window.location.assign(window.location);
				       });
				},1000);
			}
		});
	});
});