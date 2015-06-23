
function show(target) {
	target.removeClass("hidden");
}

function hide(target) {
	target.removeClass("hidden");
	target.addClass("hidden");
}

function activeDatePicker() {
    $('.datepicker').datepicker({
        icons: {
            time: "fa fa-clock-o",
            date: "fa fa-calendar",
            up: "fa fa-arrow-up",
            down: "fa fa-arrow-down"
        },
        format: "dd/mm/yyyy"
    });
}

$(document).ready(function(){
	
	activeDatePicker();
	
	/////////////////// Drop-down button setting///////////////////////////////
	// set drop-down control value after loaded
	$('.btn.btn-default.dropdown-toggle').each(function(){
		
		// get drop down value in hidden input
		var dropdownVal = $(this).parent().siblings("input").val();
		
		var selectedOption = $(this).siblings("ul").find("li a[name=" + dropdownVal + "]");
		selectedOption.addClass("selected");
		$(this).text(selectedOption.text());
		$(this).attr("name", selectedOption.attr("name"));
	});
	
	// store drop-down info to hidden input
	$("section.content").on("click", ".dropdown-menu li a",function(){
		
		$(this).parent().siblings().find("a").removeClass("selected");
		$(this).addClass("selected");
		var test = $(this).parents("ul.dropdown-menu");
		var btn = $(this).parents("ul.dropdown-menu").siblings("button.btn.btn-default.dropdown-toggle");
		btn.text($(this).text());
		var hiddenInput = $(this).parents(".form-group").find("input[type=hidden]");
		hiddenInput.attr("value", $(this).attr("name"));
		
		if ($(".region") != null && hiddenInput.is("#inputRole")) {
			showDetailInfoRegion($(this).text());
		}

		});
	/////////////////// Drop-down button setting END///////////////////////////////
});