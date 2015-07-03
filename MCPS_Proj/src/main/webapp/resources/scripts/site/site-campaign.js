
$(document).ready(function(){
	// click delete account
	$(".delete-anchor").on("click", function(){
		var campaignId = $(this).parent().siblings("input.id-campaign").val();
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