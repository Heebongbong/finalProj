/**
 * 
 */
	
function fileUpload(){
	let fileInput = document.getElementById("upfile").files;
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

function checkbox_click(self){
	if($(self).parent().hasClass('checked')){
		$(self).parent().removeClass('checked');
	}else{
		$(self).parent().addClass('checked');
	}
}

function check() {
	if ($("input:checkbox[name='category']").is(":checked")==false) {
		alert("카테고리를 하나 이상 선택해 주십시오.");
		return false;
	}
}