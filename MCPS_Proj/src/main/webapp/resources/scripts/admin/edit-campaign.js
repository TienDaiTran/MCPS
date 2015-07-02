function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#imgBanner').attr('src', e.target.result);
        }

        reader.readAsDataURL(input.files[0]);
    }
}

$(document).ready(function(){
    // Replace the <textarea id="editor1"> with a CKEditor
    // instance, using default configuration.
    CKEDITOR.replace('editor1');
    setDatePickerStartDate($("#datetimepickerPublishDay"), false);
	
	$("#bannerInputFile").change(function(){
    	readURL(this);
	});
	
	// check form create campaign submit
	$("#campaignInfoForm").submit(function(e){
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
				show($(this).parents("div.form-group").find("label.has-error"));
			}
		});
		if (result == true) {
			// update ckeditor value
			for(var instanceName in CKEDITOR.instances)
			    CKEDITOR.instances[instanceName].updateElement();
			// get form data
			var formData = new FormData($(this)[0]);
	    $.ajax({
	        url: $(this).attr("action"),
	        type: 'POST',
	        data:  formData,
	        mimeType:"multipart/form-data",
	        contentType: false,
	        cache: false,
	        processData:false,
	        success: function(msg)
	        {
	        	if ("success" == msg) {
					$("#toast").contents()[2].nodeValue = "Chỉnh sửa chiến dịch thành công";
					$("#toast").fadeIn();
					setTimeout(function(){
					       $("#toast").fadeOut();
					},1000);
				} else {
					$("#toast").contents()[2].nodeValue = "Chỉnh sửa chiến dịch thất bại";
					$("#toast").fadeIn();
					setTimeout(function(){
					       $("#toast").fadeOut();
					},1000);
				}
	        },
	        error: function(jqXHR, textStatus, errorThrown) 
	        {
	        }          
	    });
		}
		return false;
	  });
	/////////////////////////////////////////////////////////
});