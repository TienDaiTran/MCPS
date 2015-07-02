$(document).ready(function(){
	// check form create campaign submit
	$("#campaignSearchForm").submit(function(e){
		e.preventDefault();

		    $.post($(this).attr("action"), $(this).serialize(), function(msg, status) {
				if ("success" == status) {
					$("#listAccountDiv").html(msg);
					$("#example1").dataTable();
				}
			});
		return false;
	  });
	/////////////////////////////////////////////////////////
	// click delete account
	$("#listAccountDiv").on("click", ".delete-anchor", function(){
		var campaignId = $(this).parent().siblings("td.id-campaign").text();
		$.post(window.location + "/delete", {id : campaignId}, function(msg) {
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