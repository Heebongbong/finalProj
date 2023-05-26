<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="loginUser" value="${sessionScope.LoginUser}"/>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<c:set var="dto" value="${content }"/>
<script>
</script>

<div id="forget_wrap">
	<form id="forgetForm" action="${ctxPath }/user/forgetOk" method="post">
		<input class="phone_user_no" type="hidden" name="user_no" value="123">
	
		<div>
			<p>비밀번호 찾기</p>
		</div>	
	 	<div>
	 		<div>
				<p class="phone_wrap">전화번호</p>
				<input name="input_phone" class="noWhitespace" id="input_phone" placeholder="휴대폰 번호(-없이 숫자만 입력)">
				<button type="button" id="sendBtn" onclick="sendSMS()">인증번호발송</button>
				<p class="input_phoneError">&nbsp;</p>
	 		</div>
			<div class="code_wrap">
				<p class="text">인증번호</p>
				<input name="code" id="input_code" class="noWhitespace">
				<button type="button" id="checkBtn" onclick="checkCode()">인증하기</button>
				<p class="codeError">&nbsp;</p>
			</div>
			<div class="pwd_wrap">
				<p class="text">비밀번호</p>
				<input type="password" id="pwd" name="pwd" class="noWhitespace">
				<p class="pwdError">&nbsp;</p>
				<p class="text">비밀번호 확인</p>
				<input type="password" id="pwd_re" name="pwd_re" class="noWhitespace">
				<p class="pwd_reError">&nbsp;</p>
			</div>
		</div>
		
		<button type="submit" id="submitBtn">가입하기</button>
		
	</form>
</div>