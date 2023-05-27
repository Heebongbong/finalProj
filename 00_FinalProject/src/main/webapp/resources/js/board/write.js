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
	  	let currentHashtags = plusValue.split('#'); // 여러 개의 해시태그가 해시(#)로 구분되어 있다고 가정
	  	let uniqueHashtags = removeDuplicates(currentHashtags);
	  	let newValue = uniqueHashtags.join('#'); // 중복 제거된 해시태그를 다시 해시로 구분하여 문자열로 변환
	  	hashtagInput.value = newValue;
		
	}else {
		
	 	// HTML 요소 선택
	  	let hashtagInput = document.querySelector('.hashtag');

	  	// 중복 제거를 적용하여 값을 설정
	  	let currentValue = hashtagInput.value;
	  	let currentHashtags = currentValue.split('#'); 
	  	// 선택 해제한 category 해시태그 목록에서 삭제
	  	let uniqueHashtags = currentHashtags.filter((element) => element !== self.value);
	  	let newValue = uniqueHashtags.join('#');
	  	hashtagInput.value = newValue;
	}
		  
}

function check() {
	if ($("input:checkbox[name='category']").is(":checked")==false) {
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
