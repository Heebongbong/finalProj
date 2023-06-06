<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="loginUser" value="${sessionScope.LoginUser}"/>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<c:set var="dto" value="${content }"/>
<div id="forget_wrap">
	<form id="forgetForm" action="${ctxPath }/user/forgetOk" method="post">
		<input class="phone_user_no" type="hidden" name="user_no" value="123">
			
		<div id="title">비밀번호 찾기</div>
		
	 	<div class="text_part">
	 	
	 		<div class="authen_part">
	 			<p class="text">전화번호</p>
		 		<div class="authen_box">
					<input name="input_phone" class="noWhitespace" id="input_phone" placeholder="숫자만 입력">
					<button type="button" id="sendBtn" onclick="sendSMS()">인증번호발송</button>
		 		</div>
		 		<p class="input_phoneError">&nbsp;</p>
		 		
		 		<p class="text">인증번호</p>
				<div class="authen_box">
					<input name="code" id="input_code" class="noWhitespace">
					<button type="button" id="checkBtn" onclick="checkCode()">인증하기</button>
				</div>
				<p class="codeError">&nbsp;</p>
			</div>
			
			
			
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
			<button type="submit" id="submitBtn">변경하기</button>
		</div>
	</form>
</div>