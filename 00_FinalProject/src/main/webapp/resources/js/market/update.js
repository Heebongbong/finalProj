/**
 * 
 */
//  function cate_hash(self){
// 	let check_id = self.id;
// 	console.log(check_id);
// 	console.log(self.value);
	
// 	let currentHashtags = "";
	
// 	if ($("#"+check_id).is(":checked") == true) {
		
// 		// HTML 요소 선택
// 	  	let hashtagInput = document.querySelector('.hashtag');

// 	  	// 중복 제거를 적용하여 값을 설정
// 	  	let plusValue = hashtagInput.value+$('.hashtag').val()+"#"+$(self).val();
// 	  	currentHashtags = plusValue.split('#'); // 여러 개의 해시태그가 해시(#)로 구분되어 있다고 가정
// 	  	let uniqueHashtags = removeDuplicates(currentHashtags);
// 	  	let newValue = uniqueHashtags.join('#'); // 중복 제거된 해시태그를 다시 해시로 구분하여 문자열로 변환
// 	  	hashtagInput.value = newValue;
		
// 	}else {
		
// 	 	// HTML 요소 선택
// 	  	let hashtagInput = document.querySelector('.hashtag');

// 		let filterStrings = self.value.split('#');
		
// 		// 공백이 포함되어 전체 삭제 --> 공백 제거
// 		let newFilterStrings = filterStrings.filter((element) => {
//   		return element !== undefined && element !== null && element !== '';
// 		});

// 	  	// 중복 제거를 적용하여 값을 설정
// 	  	let currentValue = hashtagInput.value;
// 	  	currentHashtags = currentValue.split('#');
// 	  	let uniqueHashtags = currentHashtags.filter(a => !newFilterStrings.some(v => a.includes(v)));
// 	  	let newValue = uniqueHashtags.join('#');
// 	  	hashtagInput.value = newValue;
// 	}
		  
// }
 

// function loc_hash(){

// 	// HTML 요소 선택
// 	let hashtagInput = document.querySelector('.hashtag');
	
// 	let filterStrings = ['서울','경기','강원', '충청', '전라', '경상', '제주'];

// 	// 중복 제거를 적용하여 값을 설정
//   	let currentValue = hashtagInput.value;
//   	let currentHashtags = currentValue.split('#'); 
  	
//   	//filterStrings의 단어가 들어가지 않은 문장 찾기
//   	let filteredTexts = currentHashtags.filter(a => !filterStrings.some(v => a.includes(v)));
  	
//   	let newValue = filteredTexts.join('#');
//   	hashtagInput.value = newValue;

// 	$('.hashtag').val($('.hashtag').val()+"#"+$('#subject').val());
// }

// 해시태그 중복 제거
// function removeDuplicates(array) {
//     let uniqueArr = array.filter((element, index) => {
//       return array.indexOf(element) === index;
//     });
//     return uniqueArr;
// }


// function loc_hash(self){
	
// }


// function radiobox(radio) {
// 	let li = radio.parentNode;
// 	let isChecked = radio.checked;
  
// 	// 모든 라디오 버튼의 checked 클래스를 제거
// 	let radios = document.querySelectorAll('.obj_ul input[name="category"]');
// 	radios.forEach((radio) => {
// 	  radio.parentNode.classList.remove("checked");
// 	});
  
// 	if (isChecked) {
// 	  li.classList.add("checked");
// 	}
// }

// function radioloc(radio) {
// 	let li = radio.parentNode;
// 	let isChecked = radio.checked;

// 	// 모든 라디오 버튼의 checked 클래스를 제거
// 	let radios = document.querySelectorAll('.loc_ul input[name="category"]');
// 	radios.forEach((radio) => {
// 		radio.parentNode.classList.remove("checked");
// 	});

// 	if (isChecked) {
// 		li.classList.add("checked");
// 	}
// }

function check() {
	let count = $("input:radio[name='category']:checked").length;
	if (count < 1 ) {
		alert("카테고리를 선택해 주십시오.");
		return false;
	}
}

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

function radio_click(self){
	$('.loc_li').removeClass('checked');
	$(self).parent().addClass('checked');
	
}