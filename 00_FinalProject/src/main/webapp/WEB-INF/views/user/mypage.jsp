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
</script>
<div id="mypage_wrap">
	<div id="mypage_container_center">
		<form id="mypageForm" action="${ctxPath }/user/mypageOk" method="post" enctype="multipart/form-data" novalidate>
			<input type="hidden" name="user_no" value="${loginUser.user_no}">
			<input type="hidden" name="profile_type" id="profile_type" value="${loginUser.profile_type}">
			<input type="hidden" name="phone" id="phone" value="${loginUser.phone}">
			<input type="hidden" name="type" value="${loginUser.type }">
			<input type="hidden" name="profile" id="profile_hidden" value="${loginUser.profile }">
			<div class="text_part">
				<div class="profile_part">
				
					<div id="title">내 정보 변경</div>
					
					<div class="profile">
						
						<div>
							<label for="profileInput"> <img id="previewImg" src="${loginUser.profile }"/> </label>
							<input style="display: none;" type="file" name="upfile" id="profileInput" onchange="previewProfileImage(event)">
						</div>
						<c:if test="${loginUser.type eq 'K'|| loginUser.type eq 'N'}">
							<input class="social_profie_btn" type="button" onclick="changeProfileType()" value="소셜 프로필 적용">
						</c:if>
						<c:if test="${loginUser.authen }">
							<p class="text">인증 회원</p>
						</c:if>
						<c:if test="${!loginUser.authen }">
							<p class="text">비인증 회원</p>
						</c:if>
					</div>
					
					<c:if test="${loginUser.authen }">
						<p class="text">전화번호 변경</p>
					</c:if>
					<c:if test="${!loginUser.authen }">
						<p class="text">전화번호 등록</p>
					</c:if>
					
					<div class="authen_box">
						<input name="input_phone" class="noWhitespace" id="input_phone" value="${loginUser.phone }" placeholder="숫자만입력">
						<button type="button" id="sendBtn" onclick="sendSMS()">인증번호발송</button>
					</div>
					<p class="input_phoneError">&nbsp;</p>
					
					
					<p class="text">전화번호 확인</p>
					<div class="authen_box">
						<input name="code" id="input_code" class="noWhitespace">
						<button type="button" id="checkBtn" onclick="checkCode()">인증하기</button>
					</div>
					<p class="codeError">&nbsp;</p>
				</div>
				
				<div class="text_part_box">
					<p class="text">이메일</p>
					<input name="email" class="noWhitespace" value="${loginUser.email }" readonly>
					<p class="emailError">&nbsp;</p>
				</div>
				
				<div class="text_part_box">
					<p class="text">닉네임</p>
					<input name="nickname" class="noWhitespace" value="${loginUser.nickname }">
					<p class="nicknameError">&nbsp;</p>
				</div>
				
				<p class="text">비밀번호</p>
				<div class="authen_box">
					<input type="password" name="pwd_check" id="check_pwd" class="noWhitespace">
					<button type="button" onclick="checkPwd()">비밀번호 확인</button>
				</div>
				<p class="pwd_checkError">&nbsp;</p>
				
				<div class="text_part_box">
					<p class="text">새 비밀번호</p>
					<input type="password" id="pwd" name="pwd" class="noWhitespace">
					<p class="pwdError">&nbsp;</p>
				</div>
				
				<div class="text_part_box">
					<p class="text">새 비밀번호 확인</p>
					<input type="password" id="pwd_re" name="pwd_re" class="noWhitespace">
					<p class="pwd_reError">&nbsp;</p>
				</div>
			</div>
			<div class="modify">
				<button type="submit" id="submitBtn">수정하기</button>
			</div>
		</form>
	</div>
</div>