/**
 * 
 */


function fileUpload(){
	let fileInput = document.getElementById("files").files;
	if (fileInput.length > 0) {
		let table = "";
		for (let j = 0; j < fileInput.length; j++) {
			table += "<div class='upload_img'>" +
						"<img src='"+ctxPath+"/resources/images/icon/basis_img.png'>" +
						"<p>" +
						fileInput[j].name +
						"</p>" +
						"</div>";
						
		}
		$('.img_container').html(table);
	}
}

function check() {
	let ca_count = $("input:checkbox[name='category']:checked").length;
	let ra_count = $("input:radio[name='category']:checked").length;
	if (ca_count < 1 ) {
		alert("카테고리를 하나 이상 선택해 주십시오.");
		return false;
	}else if(ra_count < 1){
		alert("지역을 선택해 주십시오.");
		return false;
	}
}

	
function checkbox_click(self){
	if($(self).parent().hasClass('checked')){
		$(self).parent().removeClass('checked');
	}else{
		$(self).parent().addClass('checked');
	}
}

function radio_click(self){
	$('.loc_li').removeClass('checked');
	$(self).parent().addClass('checked');
	
}