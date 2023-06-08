/**
 * 
 */
function cate_hash(self){
	let check_id = self.id;
	console.log(check_id);
	console.log(self.value);
	
	let currentHashtags = "";
	
	if ($("#"+check_id).is(":checked") == true) {
		
		// HTML 요소 선택
	  	let hashtagInput = document.querySelector('.hashtag');

	  	// 중복 제거를 적용하여 값을 설정
	  	let plusValue = hashtagInput.value+$('.hashtag').val()+"#"+$(self).val();
	  	currentHashtags = plusValue.split('#'); // 여러 개의 해시태그가 해시(#)로 구분되어 있다고 가정
	  	let uniqueHashtags = removeDuplicates(currentHashtags);
	  	let newValue = uniqueHashtags.join('#'); // 중복 제거된 해시태그를 다시 해시로 구분하여 문자열로 변환
	  	hashtagInput.value = newValue;
		
	}else {
		
	 	// HTML 요소 선택
	  	let hashtagInput = document.querySelector('.hashtag');

	  	// 중복 제거를 적용하여 값을 설정
	  	let currentValue = hashtagInput.value;
	  	currentHashtags = currentValue.split('#'); 
	  	// 선택 해제한 category 해시태그 목록에서 삭제
	  	let uniqueHashtags = currentHashtags.filter((element) => element !== self.value);
	  	let newValue = uniqueHashtags.join('#');
	  	hashtagInput.value = newValue;
	}
		  
}

function check() {
	if ($("input:checkbox[class='category']").is(":checked")==false) {
		alert("적어도 하나는 선택하여 주십시오.");
		return false;
	}
}

// 해시태그 중복 제거
function removeDuplicates(array) {
    let uniqueArr = array.filter((element, index) => {
      return array.indexOf(element) === index;
    });
    return uniqueArr;
  }


function checkbox(checkbox) {
	let li = checkbox.parentNode;
	if (checkbox.checked) {
		li.classList.add("checked");
	} else {
		li.classList.remove("checked");
	}
}

function fileUpload(){
	let fileInput = document.getElementById("upfile").files;
	
	if (fileInput.length > 0) {
	  for (let j = 0; j < fileInput.length; j++) {
		console.log(fileInput[j].name); // 파일명 출력
		let container = document.createElement("div"); // <div> 요소 생성
		container.classList.add("upload_img"); // 클래스 "img" 추가
		
		let img = document.createElement("img");
		img.src = ctxPath+'/resources/images/icon/basis_img.png';
		
		let text = document.createElement("p");
		
		text.innerHTML = fileInput[j].name; // 파일명을 <p> 요소에 설정
		container.appendChild(img); // <div> 요소에 <img> 요소 추가
		container.appendChild(text); // <div> 요소에 <p> 요소 추가
		document.getElementById("upfile").parentNode.appendChild(container); // <input> 요소의 부모 요소에 <div> 요소 추가
	  }
	}
  }