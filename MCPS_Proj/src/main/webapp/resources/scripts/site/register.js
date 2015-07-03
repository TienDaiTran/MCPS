function showDetailInfoRegion(chosenRole) {
	if ("Partner" == chosenRole) {
		$("#detailInfoDiv").html($("#partnerInfo").html());
	} else 	if ("Reviewer" == chosenRole) {
		$("#detailInfoDiv").html($("#reviewerInfo").html());
		$('.multiselect').each(function(){
			var hiddenParent = $(this).parents('.hidden');
			if (hiddenParent.length == 0) {
				$(this).multiselect({
					maxHeight: 200,
					includeSelectAllOption: true
				});
			}
		});
	} else {
		$("#detailInfoDiv").html("");
	}
	activeDatePicker();
	setDatePickerEndDate($('#datetimepickerFoundingDay'), true);
	setDatePickerEndDate($('#datetimepickerBirthday'), true);
}

$(document).ready(function(){
	
	setDatePickerEndDate($('#datetimepickerFoundingDay'), true);
	setDatePickerEndDate($('#datetimepickerBirthday'), true);
	
	///////////////////CREATE ACCOUNT////////////////////////
	
	$("#inputRetypePassword").change(function(){
		hide($("#passwordMatchLabel"));
		hide($("#passwordNotMatchLabel"));
		var password = $("#inputNewPassword").val();
		var retypePassword = $("#inputRetypePassword").val();
		if (password != retypePassword) {
			show($("#passwordNotMatchLabel"));
		} else {
			show($("#passwordMatchLabel"));
		}
	});
	
	$("#inputNewPassword").change(function(){
		hide($("#passwordMatchLabel"));
		hide($("#passwordNotMatchLabel"));
		var password = $("#inputNewPassword").val();
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
			if (hiddenParent != null) {
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
		var password = $("#inputNewPassword").val();
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
				    	   window.location.assign(window.location);
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
});