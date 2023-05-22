<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="loginUser" value="${sessionScope.LoginUser}"/>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<c:set var="dto" value="${content }"/>


<style type="text/css">
	
	div {
		border: 0;
	}
	
	p {
		font-size: .8em;	
	}
	
	.text {
		font-size: 1em;
	}
	
	#mypage_wrap {
		text-align: center;
		padding-top: 60px;
	}
	
	#previewImg {
		width: 150px;
		height: 150px;
		border-radius: 50%;
	}
	
	
	}
</style>

<div id="mypage_wrap">

	<form id="joinForm" action="${ctxPath }/user/joinOk" method="post" enctype="multipart/form-data" novalidate>
		
		<div class="text_part">
			<p class="text">이메일</p>
			<input name="email" class="noWhitespace" onkeyup="noWhitespace(this);">
			<p class="emailError">&nbsp;</p>
			
			<p class="text">닉네임</p>
			<input name="nickname" class="noWhitespace" onkeyup="noWhitespace(this);">
			<p class="nicknameError">&nbsp;</p>
			
			<p class="text">비밀번호</p>
			<input type="password" name="pwd" class="noWhitespace" onkeyup="noWhitespace(this);">
			<p class="pwdError">&nbsp;</p>
			<input type="password" name="pwd_re" class="noWhitespace" onkeyup="noWhitespace(this);">
			<p class="pwd_reError">&nbsp;</p>
			
			<div class="profile_part">
				<p>프로필 사진 (선택)</p>
				<div class="profile"><img id="previewImg" src="../resources/images/profile/default/default_profile.png"/></div>
				<div><input type="file" name="upfile" id="profileInput" onchange="previewProfileImage(event)"></div>
				
				<p class="text">전화번호(선택)</p>
				<input name="phone" class="noWhitespace" id="input_phone" placeholder="휴대폰 번호(-없이 숫자만 입력)" onkeyup="noWhitespace(this);">
				<button type="button" id="sendBtn" onclick="sendSMS()">인증번호발송</button>
				<p class="phoneError">&nbsp;</p>
				<input name="code" id="input_code" class="noWhitespace" onkeyup="noWhitespace(this);">
				<button type="button" onclick="checkCode()">인증하기</button>
				<p class="codeError">&nbsp;</p>
			</div>
		</div>
		
		<button type="submit">가입하기</button>
		
	</form>
	
</div>

<!-- jQuery validation 플러그인 -->
<!-- 간단한 클라이언트 측 양식 유효성 검사를 쉽게 할 수 있고 많은 사용자 정의 옵션을 정의할 수 있습니다. -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.3/jquery.validate.min.js"></script>
<script type="text/javascript">
	
	// 공백제거
	var elements = document.querySelectorAll('.noWhitespace');
	for (var i = 0; i < elements.length; i++) {
	  elements[i].addEventListener('input', function(event) {
	    var currentValue = this.value;
	    var trimmedValue = currentValue.replace(/\s/g, '');
	    if (currentValue !== trimmedValue) {
	      this.value = trimmedValue;
	    }
	  });
	}
	
	// 숫자만 입력
  var inputElement = document.getElementById("input_phone");

  inputElement.addEventListener("input", function(event) {
    var currentValue = inputElement.value;
    var sanitizedValue = currentValue.replace(/\D/g, "");

    if (currentValue !== sanitizedValue) {
      inputElement.value = sanitizedValue;
    }
  });
	
	
	function sendSMS(event) {
			
		
			let phoneError = document.getElementsByClassName("phoneError")[0];
			const phone = document.getElementById("input_phone").value;
				
			if(phoneError.innerText === '') {
				
				// 전송 버튼 클릭 후 재전송 버튼으로 바꾸기
			  const button = document.getElementById('sendBtn');
			  button.innerText = '재전송';
			  
			  const phone = document.getElementById("input_phone").value;
			  
			  alert(phone);
			  $.ajax({
				  url : "${ctxPath}/user/sms/send",
				  type : "post",
				  data : { phone : phone },
				  success : function(res) {
					  phoneError.textContent = "휴대전화로 인증번호가 전송되었습니다.";
				  },
				  error : function() {
					  alert("전송 실패");
				  }
				  
			  });
			  
			} else {
				 phoneError.textContent = "휴대전화를 입력하세욘";
			}
			event.preventDefault();


	}
	  

	  
	  
	
	function checkCode() {
		
		event.preventDefault();
		
		let phoneError = document.getElementsByClassName("phoneError")[0];
		
		const button = document.getElementById('sendBtn');
		const buttonText = button.innerText;
		
		if(buttonText === '재전송'){
			
		  const input_code = document.getElementById("input_code").value;
		  let codeError = document.getElementsByClassName("codeError")[0];
		  
		  $.ajax({
			  url : "${ctxPath}/user/sms/check",
			  type : "post",
			  data : { input_code : input_code },
			  success : function(res) {
				  if(res == "true"/*  && input_code.length != 0 */){
					  codeError.textContent = "인증 완료 회원가입 go";
				  }else {
					  codeError.textContent = "인증 번호가 틀렸습니다.";
				  }
			  },
			  error : function() {
				  alert("인증 실패");
			  }
			  
		  });
		  
		} else {
			phoneError.textContent = "휴대전화를 입력하세욘";
		}
		
	  
		
	}
    
	

	$(document).ready(function(){

		$.validator.addMethod("engAndNum", function(value, element) {
			   var pattern = /^[A-Za-z0-9]*$/;
		
			   return pattern.test(value);
			});
		
			$.validator.addMethod("specialChars", function(value, element) {
			   // Define the pattern to match special characters
			   var pattern = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/;
		
			   // Test the value against the pattern and return true or false
			   return pattern.test(value);
			});
		
			$.validator.addMethod("capitalLetters", function(value, element) {
			   // Define the pattern to match capital letters
			   var pattern = /[A-Z]/;
		
			   // Test the value against the pattern and return true or false
			   return pattern.test(value);
			});
		
			$.validator.addMethod("emailCheck", function(value, elements) {
			   var pattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
		
			   return pattern.test(value);
		
			});
		
			$.validator.addMethod("phoneCheck", function(value, elements) {
					var length = value.trim().length;
				  
			  		if(length == 0){
		        	return true;
		        }else {
						  var pattern = /^010\d{7,8}$/;
						  return pattern.test(value);
		        }
				  
			});
			
		 $.validator.addMethod("phoneLength", function(value, element) {
		        var length = value.trim().length;
		        
		        if(length == 0){
		        	return true;
		        }else {
			        return length >= 10 && length <= 11;
		        }
		 });
		 
		 $.validator.addMethod("codeLength", function(value, element) {
			  const button = document.getElementById('sendBtn');
				const buttonText = button.innerText;
	      var length = value.trim().length;
				if(buttonText === '재전송'){
			        return length = 6;
		        }else {
		        	return true;
		        }
		 });
		 
		 $.validator.addMethod("codeCheck", function(value, element) {
			  const button = document.getElementById('sendBtn');
				const buttonText = button.innerText;
	      var length = value.trim().length;
				if(buttonText === '재전송'){
			        return length != 0;
		        }else {
		        	return true;
		        }
		 });
		 
		 $.validator.addMethod("nicknameLength", function(value, element) {
		        var length = value.trim().length;
		        return length >= 3 && length <= 20;
		 });
		 
     $.validator.addMethod("noSpecialChars", function(value, element) {
         var pattern = /^[^!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]*$/;
         return pattern.test(value);
     });

		 $.validator.addMethod("nicknameUnique", function(value, element) {
		        
			 let isUnique = false;
			 // AJAX 요청
		        $.ajax({
		            url: "${ctxPath}/user/check/nickname",
		            type: "get",
		            data: { nickname: value },
		            async : false,
		            datatype : "text",
		            success : function(result) {
		            	isUnique = result;
		            	console.log(result);
		             },
		            error : function (){
		            	 console.log(1);
		             }
		        });
		        return isUnique;
		 });
		 
		 $.validator.addMethod("phoneUnique", function(value, element) {
		    
			 
			 let isUnique = false;
			 
			 var length = value.trim().length;
		        
		        if(length == 0){
		        	return true;
		        }else {
		        
			 // AJAX 요청
		        $.ajax({
		            url: "${ctxPath}/user/check/phone",
		            type: "get",
		            data: { phone: value },
		            async : false,
		            datatype : "text",
		            success : function(result) {
		            	isUnique = result;
		            	console.log(result);
		             },
		            error : function (){
		            	 console.log(1);
		             }
		        });
		        return isUnique;
		        
		        }
		 });
		 
		$("#joinForm").validate({
			
			rules : {
					pwd : {
		         required: true,
		         minlength: 6,
		         maxlength: 12,
		         specialChars: true,
		         capitalLetters: true
		      },
		      pwd_re : {
		         required: true,
		         minlength: 6,
		         maxlength: 12,
		         specialChars: true,
		         capitalLetters: true,
		         equalTo: ".pwd"
		      },
					email : {
						required : true,
						emailCheck : true,
						email : true
					},
					phone : {
						phoneLength : true,
						phoneCheck : true,
						phoneUnique : true
					}, 
					code : {
						codeCheck : true,
						codeLength : true
					},
					nickname : {
						nicknameLength : true,
						nicknameUnique : true,
	          noSpecialChars : true
					}
			},	
			messages : {
				pwd : {
	         required : "비밀번호 입력은 필수 입니다.",
	         minlength : "최소 6글자 이상 입력해주세요.",
	         maxlength : "12글자를 넘지 말아주세요.",
	         specialChars : "특수문자 입력해주세요.",
	         capitalLetters: "대문자 하나 입력해주세요"
	      },
	      pwd_re : {
	         required: "중복체크는 필수 입니다.",
	         minlength : "최소 6글자 이상 입력해주세요.",
	         maxlength : "12글자를 넘지 말아주세요.",
	         specialChars : "특수문자 입력해주세요.",
	         capitalLetters: "대문자 하나 입력해주세요",
	         equalTo: "일치하지 않아요...."

	      },
				email : {
					required: '이메일을 입력해주세요',
					emailCheck : '유효한 이메일 주소를 입력하세요',
					email : '유효한 이메일 주소를 입력하세요'
				},
				phone : {
					phoneLength : "유효한 휴대전화 번호를 입력하세욘",
					phoneCheck : "공백, 문자 없이 입력하세요",
					phoneUnique : "이미 등록된 번호입니다."
				},
				code : {
					codeCheck : "인증번호를 입력하세요",
					codeLength : "인증번호는 6자리입니다."
				},
				nickname : {
					nicknameLength : "닉네임은 3글자에서 20글자 사이어야 합니다.",
					nicknameUnique : "이미 사용 중인 닉네임입니다.",
	        noSpecialChars : "특수문자는 입력할 수 없습니다."
				}
			},
			
      errorPlacement: function(error, element) {
          var targetElementClass = element.attr("name") + "Error";
          var targetElement = $("." + targetElementClass);
          targetElement.html(error);
          targetElement.css("color", "red");
      },
      
      success: function(label, element) {
    	    var targetElementClass = $(element).attr("name") + "Error";
    	    var targetElement = $("." + targetElementClass);
          targetElement.css("height", "17px");
    	}

		});
		
	});


		
		
		
		// 비밀번호 일치 확인
		$('.pw').keyup(function() {
			var pass1 = $("#password_1").val();
			var pass2 = $("#password_2").val();
			
			if(pass1 != "" || pass2 != "") {
				if(pass1 == pass2) {
					$("#checkPw").html('비밀번호 일치');
					$("#checkPw").css('color', 'green');
				} else if (pass1 != pass2) {
					$("#checkPw").html('비밀번호 불일치');
					$("#checkPw").css('color', 'red');
				}
			}
			
		})
		
		// 바꾼 프로필 이미지 보여주기
		function previewProfileImage(event) {
			var input = event.target;
			var reader = new FileReader();
			reader.onload = function() {
				var previewImg =  document.getElementById('previewImg');
				previewImg.src = reader.result;
			};
			reader.readAsDataURL(input.files[0]);
		}
		
		
		
		/* 문자, 띄어쓰기, 코드 세션 만료 */
</script>

