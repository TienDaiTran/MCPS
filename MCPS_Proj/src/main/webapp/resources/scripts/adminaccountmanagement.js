$(document).ready(function(){
	
	var selectedOption = $('.btn.btn-default.dropdown-toggle').siblings("ul").find("li a.selected");
	$('.btn.btn-default.dropdown-toggle').text(selectedOption.text());
	$('.btn.btn-default.dropdown-toggle').attr("name", selectedOption.attr("name"));
	
	$(".dropdown-menu li a").click(function(){
		
		$(this).parent().siblings().find("a").removeClass("selected");
		$(this).addClass("selected");
		var btn = $('.btn.btn-default.dropdown-toggle');
		btn.text($(this).text());
		var hiddenInput = $(".form-group input[type=hidden]");
		hiddenInput.attr("value", $(this).attr("name"));
		});
	
	$("#btnSearch").click(function(){
		var accountId = $("#inputID").val();
		var email = $("#inputEmail").val();
		var roleId = $("#inputRole").val();
		$.post("./dashboard/account", {id : accountId, email : email, role : roleId}, function(msg, status) {
			if ("success" == status) {
				$("#listAccountDiv").html(msg);
				$("#example1").dataTable();
			}
		});
	});
	
	$("#listAccountDiv").on("click", ".block-anchor", function(){
		var accountId = $(this).parent().siblings("td.id-account").text();
		$.post("./dashboard/account", {id : accountId, block : "true"}, function(msg) {
			if ("success" == msg) {
				$("#toast").contents()[2].nodeValue = "block success";
				$("#toast").fadeIn();
				setTimeout(function(){
				       $("#toast").fadeOut(function(){
				    	   window.location.assign("./dashboard");
				       });
				},1000);
			}
		});
	});
	
	$("#listAccountDiv").on("click", ".unblock-anchor", function(){
		var accountId = $(this).parent().siblings("td.id-account").text();
		$.post("./dashboard/account", {id : accountId, block : "false"}, function(msg) {
			if ("success" == msg) {
				$("#toast").contents()[2].nodeValue = "unblock success";
				$("#toast").fadeIn();
				setTimeout(function(){
				       $("#toast").fadeOut(function(){
				    	   window.location.assign("./dashboard");
				       });
				},1000);
			}
		});
	});
	
	$("#listAccountDiv").on("click", ".delete-anchor", function(){
		var accountId = $(this).parent().siblings("td.id-account").text();
		$.post("./dashboard/account/delete", {id : accountId}, function(msg) {
			if ("success" == msg) {
				$("#toast").contents()[2].nodeValue = "delete success";
				$("#toast").fadeIn();
				setTimeout(function(){
				       $("#toast").fadeOut(function(){
				    	   window.location.assign("./dashboard");
				       });
				},1000);
			}
		});
	});
});