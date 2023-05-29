/**
 * 
 */
 
function check() {
	if ($("input:checkbox[name='category']").is(":checked")==false) {
		alert("적어도 하나는 선택하여 주십시오.");
		return false;
	}
}

function loc_hash(){
	$('.hashtag').val($('.hashtag').val()+"#"+$('#subject').val());
}