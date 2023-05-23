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
	
</style>

<div id="mypage_wrap">

	<form id="mypageForm" action="${ctxPath }/user/mypageOk" method="post" enctype="multipart/form-data" novalidate>
		
		<input type="hidden" name="profile_type" id="profile_type" value="${loginUser.profile_type}">
		<input type="hidden" name="phone" id="phone" value="${loginUser.phone}">
		
		<div class="text_part">
			<div class="profile_part">
				<p>프로필 사진</p>
				<div class="profile"><img id="previewImg" src="${loginUser.profile }"/></div>
				<div><input type="file" name="upfile" id="profileInput" onchange="previewProfileImage(event)"></div>
				<c:if test="${loginUser.type ne 'S'|| loginUser.type ne 'G'}">
					<input type="button" onclick="changeProfileType()" value="소셜 프로필 적용">
				</c:if>
				
				<p class="text">전화번호</p>
				<input name="input_phone" class="noWhitespace" id="input_phone" value="${loginUser.phone }" placeholder="휴대폰 번호(-없이 숫자만 입력)">
				<button type="button" id="sendBtn" onclick="sendSMS()">인증번호발송</button>
				<p class="input_phoneError">&nbsp;</p>
				<p class="text">전화번호 확인</p>
				<input name="code" id="input_code" class="noWhitespace">
				<button type="button" onclick="checkCode()">인증하기</button>
				<p class="codeError">&nbsp;</p>
			</div>
			
			<p class="text">이메일</p>
			<input name="email" class="noWhitespace" value="${loginUser.email }" readonly>
			<p class="emailError">&nbsp;</p>
			
			<p class="text">닉네임</p>
			<input name="nickname" class="noWhitespace" value="${loginUser.nickname }">
			<p class="nicknameError">&nbsp;</p>
			
			<p class="text">비밀번호</p>
			<input type="password" name="pwd_check" id="check_pwd">
			<button type="button" onclick="checkPwd()">비밀번호 확인</button>
			<p class="pwd_checkError">&nbsp;</p>
			<p class="text">새 비밀번호</p>
			<input type="password" id="pwd" name="pwd" class="noWhitespace">
			<p class="pwdError">&nbsp;</p>
			<p class="text">새 비밀번호 확인</p>
			<input type="password" id="pwd_re" name="pwd_re" class="noWhitespace">
			<p class="pwd_reError">&nbsp;</p>
			
		</div>
		
		<button type="submit">가입하기</button>
		
	</form>
	
</div>

<!-- jQuery validation 플러그인 -->
<!-- 간단한 클라이언트 측 양식 유효성 검사를 쉽게 할 수 있고 많은 사용자 정의 옵션을 정의할 수 있습니다. -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.3/jquery.validate.min.js"></script>
<script type="text/javascript">
	
	/* 
	소셜회원(=== K,G,N)일 경우 S로 변경 
	사이트회원일 경우 회원에 맞는 소셜타입으로 변경
	mypage페이지 접근 시 토큰 업데이트해서 userprofile 받아와서 버튼 클릭시 소셜 프로필 노출하기.
	*/
	function changeProfileType() {
	
	$.ajax({
		  url : ctxPath + "/user/snsProfile",
		  type : "post",
		  dataType : "text",
		  success : function(res) {
			  console.log(res);
				$("#previewImg").attr("src", res);
				$('#profile_type').val(0);
		  },
		  error : function() {
			  alert("전송 실패");
		  }
		  
	  });
	
	//event.preventDefault();
	
	}
	
	// 비밀번호 확인
	function checkPwd() {
		alert($(".pwd_checkError").text().trim());
		var check_pwd = document.getElementById("check_pwd").value;
	  let pwdCheckError = document.getElementsByClassName("pwd_checkError")[0];
		
	  if(check_pwd != null){
		  
			$.ajax({
				  url : "${ctxPath}/user/check/pwd",
				  type : "post",
				  data : {check_pwd : check_pwd},
				  dataType : "text",
				  success : function(res) {
					  if(res === "true") {
						  pwdCheckError.textContent = "비밀번호 확인";
					  } else {
						  pwdCheckError.textContent = "비밀번호가 틀렸습니다.";
					  }
				  },
				  error : function() {
					  alert("비밀번호 확인 실패");
				  }
				  
			  });
	  } else {
		  alert("error");
	  }
		
		//event.preventDefault();
		
		}
	
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
			
			let phoneError = document.getElementsByClassName("input_phoneError")[0];
		  const phone = document.getElementById("input_phone").value;
				
			if(phone === '') {
				phoneError.textContent = "휴대전화를 입력하세요.";
			}
			
			if(phoneError.innerText === '' || phoneError.innerText === '휴대전화로 인증번호가 전송되었습니다.') {
				
				// 전송 버튼 클릭 후 재전송 버튼으로 바꾸기
			  const button = document.getElementById('sendBtn');
			  button.innerText = '재전송';

			  
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
			  
			} 
			//event.preventDefault();


	}
	  
	
	function checkCode() {
		
		event.preventDefault();
		
	  const input_code = document.getElementById("input_code").value;
		
		let phoneError = document.getElementsByClassName("input_phoneError")[0];
	  let codeError = document.getElementsByClassName("codeError")[0];
	  
		const button = document.getElementById('sendBtn');
		const buttonText = button.innerText;
		
		if(buttonText === '재전송'){
			
		  
			  if(input_code != null){
				  $.ajax({
					  url : "${ctxPath}/user/sms/check",
					  type : "post",
					  data : { input_code : input_code },
					  success : function(res) {
						  if(res == "true"/*  && input_code.length != 0 */){
							  codeError.textContent = "인증 완료";
							  let phone = $('#input_phone').val();
							  $('#phone').val(phone);
						  }else {
							  codeError.textContent = "인증 번호가 틀렸습니다.";
						  }
					  },
					  error : function() {
						  alert("인증 실패");
					  }
					  
				  });
			  }	else {
		 			 codeError.textContent = "인증번호 발송 버튼을 눌러 인증번호를 받으세요";
			  }  
		  
		} else {
			if(phoneError.innerText === '') {
				codeError.textContent = "인증번호 발송 버튼을 눌러 인증번호를 받으세요";
			} else {
				codeError.textContent = "휴대전화번호를 입력하세요";
			}
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

     
     
     
     
     $.validator.addMethod("minlength1", function(value, element) {
    	 var length = value.trim().length;

    	 if($(".pwd_checkError").text().trim() === "비밀번호 확인" || length != 0 ){
	    		 var length = value.trim().length;
	         return length >= 6;
    	 }else {return true}
     });

     $.validator.addMethod("maxlength1", function(value, element) {
    	 var length = value.trim().length;
		  
    	 if($(".pwd_checkError").text().trim() === "비밀번호 확인" || length != 0 ){
    	   var length = value.trim().length;
         return length <= 20;
    	 }else {return true}
     });

     $.validator.addMethod("specialChars1", function(value, element) {
    	 var length = value.trim().length;
		  
    	 if($(".pwd_checkError").text().trim() === "비밀번호 확인" || length != 0 ){
	    	  // Define the pattern to match special characters
			   var pattern = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/;
		
			   // Test the value against the pattern and return true or false
			   return pattern.test(value);
    	 }else {return true}
     });

     $.validator.addMethod("capitalLetters1", function(value, element) {
    	 var length = value.trim().length;
		  
    	 if($(".pwd_checkError").text().trim() === "비밀번호 확인" || length != 0 ){
    	// Define the pattern to match capital letters
		   var pattern = /[A-Z]/;
	
		   // Test the value against the pattern and return true or false
		   return pattern.test(value);
    	 }else {return true}
     });

     $.validator.addMethod("equalTo1", function(value, element) {
    	 var length = value.trim().length;
		  
    	 if($(".pwd_checkError").text().trim() === "비밀번호 확인" || length != 0 ){
    		 let pwd = $("#pwd").val();
    		 let pwd_re = $("#pwd_re").val();
    		 
    		 alert(pwd);
    		 
    		 return pwd == pwd_re;
    	 }else {return true}
     });
     

     
     
     $.validator.addMethod("nicknameUnique", function(value, element) {
		        
			 let isUnique = false;
			 // AJAX 요청
		        $.ajax({
		            url: "${ctxPath}/user/check/nickname",
		            type: "get",
		            data: 
		            	{ nickname: value },
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
			 
			 let length = value.trim().length;
		        
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
		            	 console.log("/user/check/phone 못받아유~");
		             }
		        });
		        return isUnique;
		        
		        }
		 });
		 
		$("#mypageForm").validate({
			
			rules : {
					pwd: {
						minlength1: true,
	         maxlength1: true,
	         specialChars1: true,
	         capitalLetters1: true,
		      },
		      pwd_re: {
		    	   minlength1: true,
		         maxlength1: true,
		         specialChars1: true,
		         capitalLetters1: true,
		         equalTo1: true
		      },
					email: {
						required : true,
						emailCheck : true,
						email : true
					},
					input_phone: {
						phoneLength : true,
						phoneCheck : true,
						phoneUnique : true
					}, 
					code: {
						codeCheck : true,
						codeLength : true
					},
					nickname: {
						nicknameLength : true,
						nicknameUnique : true,
	          noSpecialChars : true
					}
			},	
			messages : {
				pwd: {
					 minlength1 : "최소 6글자 이상 입력해주세요.",
					 maxlength1 : "12글자를 넘지 말아주세요.",
	         specialChars1 : "특수문자 입력해주세요.",
	         capitalLetters1: "대문자 하나 입력해주세요"
	      },
	      pwd_re: {
	    	   minlength1 : "최소 6글자 이상 입력해주세요.",
	         maxlength1 : "12글자를 넘지 말아주세요.",
	         specialChars1 : "특수문자 입력해주세요.",
	         capitalLetters1: "대문자 하나 입력해주세요",
	         equalTo1: "일치하지 않아요...."

	      },
				email: {
					required: '이메일을 입력해주세요',
					emailCheck : '유효한 이메일 주소를 입력하세요',
					email : '유효한 이메일 주소를 입력하세요'
				},
				input_phone: {
					phoneLength : "유효한 휴대전화 번호를 입력하세욘",
					phoneCheck : "공백, 문자 없이 입력하세요",
					phoneUnique : "이미 등록된 번호입니다."
				},
				code: {
					codeCheck : "인증번호를 입력하세요",
					codeLength : "인증번호는 6자리입니다."
				},
				nickname: {
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
			let input = event.target;
			let reader = new FileReader();
			reader.onload = function() {
				let previewImg =  document.getElementById('previewImg');
				previewImg.src = reader.result;
			};
			reader.readAsDataURL(input.files[0]);
			alert(previewImg.src);
			
			$('#profile_type').val(1);
			
		}
		
		
		
		/* 문자, 띄어쓰기, 코드 세션 만료 */
</script>

