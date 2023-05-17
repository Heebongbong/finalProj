<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="loginUser" value="${sessionScope.LoginUser }"/>
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

	<form action="${ctxPath }/user/joinOk" method="post" enctype="multipart/form-data">
		
		<div class="profile_part">
			<div class="profile"><img id="previewImg" src="../resources/images/logo.png"/></div>
			<div><input type="file" name="profile" id="profileInput" onchange="previewProfileImage(event)"></div>
		</div>
		
	<div class="text_part">
		<p class="text">이메일</p>
		<input name="email" class="email">
		<p class="text">비밀번호</p>
		<input type="password" class="pwd">
		<p class="text">비밀번호 확인</p>
		<input type="password" name="pwd" class="pwd">
		<p class="text">닉네임</p>
		<input name="nickname" class="nickname">
	</div>
	
	</form>
	
</div>

<script type="text/javascript">

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

