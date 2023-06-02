<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="loginUser" value="${sessionScope.LoginUser}"/>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<c:set var="dto" value="${content }"/>
<!-- jQuery validation 플러그인 -->
<!-- 간단한 클라이언트 측 양식 유효성 검사를 쉽게 할 수 있고 많은 사용자 정의 옵션을 정의할 수 있습니다. -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.3/jquery.validate.min.js"></script>
<script>
	const loginUser_phone = '${loginUser.phone}';
	const loginType = '${loginUser.type}';
	const loginEmail = '${loginUser.email}';
</script>
<div id="mypage_wrap">
		<div id="mypage_container_center">
			<form id="joinForm" action="${ctxPath }/user/joinOk" method="post" enctype="multipart/form-data" novalidate>
				
				<div id="title">회원 가입</div>
				
				<div class="text_part">
					<div class="text_part_box">
						<p class="text">이메일</p>
						<input name="email" class="noWhitespace">
						<p class="emailError">&nbsp;</p>
					</div>
					<div class="text_part_box">
						<p class="text">닉네임</p>
						<input name="nickname" class="noWhitespace">
						<p class="nicknameError">&nbsp;</p>
					</div>
					<div class="text_part_box">
						<p class="text">비밀번호</p>
						<input type="password" id="pwd" name="pwd" class="noWhitespace">
						<p class="pwdError">&nbsp;</p>
						</div>
					<div class="text_part_box">
						<p class="text">비밀번호 확인</p>
						<input type="password" id="pwd_re" name="pwd_re" class="noWhitespace">
						<p class="pwd_reError">&nbsp;</p>
					</div>
				</div>	
				
					
				
				<div class="profile">
					<p>선택 항목</p>
					<img id="previewImg" src="../resources/images/profile/default/default_profile.png"/>
					<div>
						<label for="profileInput"> / 파일선택 /</label>
						<input style="display: none;" type="file" name="upfile" id="profileInput" onchange="previewProfileImage(event)">
					</div>
				</div>
				
				<div class="authen_part">
					
					<p class="text">전화번호<span>(선택)</span></p>
					<div class="profile_box">
						<input name="phone" class="noWhitespace" id="input_phone" placeholder="휴대폰 번호(-없이 숫자만 입력)">
						<button type="button" id="sendBtn" class="profile_box_button" onclick="sendSMS()">인증번호발송</button>
					</div>	
					<p class="phoneError">&nbsp;</p>
					
					<div class="profile_box">
						<input name="code" id="input_code" class="noWhitespace">
						<button type="button" class="profile_box_button" onclick="checkCode()">인증하기</button>
					</div>
					<p class="codeError">&nbsp;</p>
				</div>
				
				<div class="join">
					<button type="submit">가입하기</button>
				</div>
			</form>
		</div>
</div>