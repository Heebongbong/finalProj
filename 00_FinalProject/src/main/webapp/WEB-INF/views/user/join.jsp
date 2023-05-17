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

	<form id="joinForm" action="${ctxPath }/user/joinOk" method="post" enctype="multipart/form-data">
		
		<div class="profile_part">
			<div class="profile"><img id="previewImg" src="../resources/images/profile/default/default_profile.png"/></div>
			<div><input type="file" name="profile" id="profileInput" onchange="previewProfileImage(event)"></div>
		</div>
		
		<div class="text_part">
			<p class="text">이메일</p>
			<input name="email" class="email">
			<p class="emailError">&nbsp;</p>
			
			<p class="text">닉네임</p>
			<input name="nickname" class="nickname">
			<p class="nicknameError">&nbsp;</p>
			
			<p class="text">비밀번호</p>
			<input type="text" name="pwd" class="pwd">
			<p class="pwdError">&nbsp;</p>
			<p class="text">비밀번호 확인</p>
			<input type="text" name="pwd_re" class="pwd_re">
			<p class="pwd_reError">&nbsp;</p>
			
			<p class="text">전화번호</p>
			<input name="phone" class="phone">
			<button>인증하기</button>
			<p class="phoneError">&nbsp;</p>
		</div>
		
		<input type="submit">
	
	</form>
	
</div>

<!-- jQuery validation 플러그인 -->
<!-- 간단한 클라이언트 측 양식 유효성 검사를 쉽게 할 수 있고 많은 사용자 정의 옵션을 정의할 수 있습니다. -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.3/jquery.validate.min.js"></script>
<script type="text/javascript">


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
				  var pattern = /^010\d{7,8}$/;
				  
				  return pattern.test(value);
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
		        // Ajax 요청을 통해 서버에 닉네임 중복 확인을 진행합니다.
		        // 이 부분은 서버의 구현에 따라 다르므로 예시 코드로 대체하겠습니다.

		        // AJAX 요청
		        return $.ajax({
		            url: "${ctxPath}/user/checkNickname",
		            type: "POST",
		            data: { nickname: value },
		            dataType: "json"
		        }).then(function(response) {
		            // 서버로부터의 응답을 처리합니다.
		            return response; // 중복된 닉네임이 아닌 경우 true 반환
		        });
		 })
     
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
		         equalTo: ".pwd"
		      },
					email : {
						required : true,
						emailCheck : true,
						email : true
					},
					phone : {
						phoneCheck : true
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
	         capitalLetters: "대문자 하나 입력해주세요",
	         specialChars : "특수문자 입력해주세요."
	      },
	      pwd_re : {
	         required: "중복체크는 필수 입니다.",
	         equalTo: "일치하지 않아요...."

	      },
				email : {
					required : '이메일을 입력해주세요',
					emailCheck : '유효한 이메일 주소를 입력하세요',
					email : '유효한 이메일 주소를 입력하세요'
				},
				phone : {
					phoneCheck : "공백, 문자 없이 입력하세요"
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
      },
      
      success: function(label, element) {
    	    var targetElementClass = $(element).attr("name") + "Error";
    	    var targetElement = $("." + targetElementClass);
    	    targetElement.text("확인");
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
		
		
		
		
</script>

