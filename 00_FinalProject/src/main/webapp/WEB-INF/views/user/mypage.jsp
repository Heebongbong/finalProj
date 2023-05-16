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
	
	#previewImg {
		width: 200px;
		height: 200px;
		border-radius: 50%;
	}
	
	#mypage_wrap {
		text-align: center;
		padding-top: 50px;
		display: grid;
    justify-items: center;
	}
	
	.cont_1 {
		border: 1px solid black;
		width: 200px;
	}
	
	.cont_2 {
		border: 1px solid black;
		width: 500px;
		
	}
</style>

<div id="mypage_wrap">

	<c:if test="${loginUser.type eq 'S'}"></c:if>
	<c:if test="${loginUser.type ne 'S'}"></c:if>

	<!-- 
		??
		1. 휴대폰 인증 처리
			- 기본 1, 수정 or 원래 값이랑 다르면 0으로 변경, 인증 확인 받으면 1로 돌려받기
			- 1일 시에 변경 가능
		2. 소셜일 시 사진 수정하면 소셜 프로필 업데이트 X
		3. 소셜 프로필로 업데이트 버튼 만들기
		4. 소셜 프로필 이미지 src에 바로 넣는 스크립트
	 -->
	<form action="${ctxPath }/user/insert" method="post" enctype="multipart/form-data">
	
	<!-- 
		<input type="hidden" name="phoneCheck" value="1">
		<input type="hidden" name="profileCheck" value="1">
	 -->
	
		<div class="cont_1">
		
			<div class="profile"><img id="previewImg" src="../resources/images/logo.png"/></div>
			<div><input type="file" name="profile" id="profileInput" onchange="previewProfileImage(event)"></div>
			<div><button>??소셜 프로필로 업데이트</button></div>
			
		</div>
		
		<div class="cont_2">
		
			<div class="phone">
				<div class="part_1">
					<div class="title">휴대폰 번호</div>
					<div class="content"><input name="phone" value="${dto.phone }" /></div>
				</div>
				<div class="part_2">
					<button onclick="location.href='${ctxPath }/user/?'">변경-> 휴대폰 인증 매핑!</button>
				</div>
			</div>
			<div class="nickname">
				<div class="title">닉네임</div>
				<div class="content"><input name="nickname" value="${dto.nickname }" /></div>
			</div>
			<div class="password">
					<div class="title">비밀번호</div>
					<div class="content1"><input name="pwd" id="password_0" type="password" placeholder="현재 비밀번호"/></div>
					<span id="checkPw_db"></span>
					<div class="content2"><input class="pw" id="password_1" type="password" placeholder="비밀번호 변경"/></div>
					<div class="content3"><input name="pwd_update" class="pw" id="password_2" type="password" placeholder="비밀번호 변경 확인"/></div>
					<span id="checkPw"></span>
			</div>
			<div class="cont_3">
				<input type="submit" value="변경하기">
			</div>
			
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

