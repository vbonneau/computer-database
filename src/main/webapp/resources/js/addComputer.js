

$("#introduced").change(function(){
	var introduced = $(this).val();
	var discontinued = $("#discontinued").val();
	checkDate(introduced, discontinued)
});

$("#discontinued").change(function(){
	var discontinued = $(this).val();
	var introduced = $("#introduced").val();
	checkDate(introduced, discontinued)
});

function checkDate(introduced, discontinued){
	if(introduced != "" && discontinued != ""){
		if(introduced > discontinued){
			$("#alertMessage").html("date not valid, the introduce date must be inferior to the discontinues date");
			$("#alertMessage").show();
			$("#addSubmit").prop('disabled', true);
		} else {
			$("#alertMessage").hide();
			$("#addSubmit").prop('disabled', false);
		} 
			
		
	}
}
