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
	<div>
		<p>비밀번호 찾기</p>
	</div>	
 	<div>
		<input name="input_phone" class="noWhitespace" id="input_phone" placeholder="휴대폰 번호(-없이 숫자만 입력)">
		<button type="button" id="sendBtn" onclick="sendSMS()">인증번호발송</button>
		<p class="input_phoneError">&nbsp;</p>
		<p class="text">전화번호 확인</p>
		<input name="code" id="input_code" class="noWhitespace">
		<button type="button" id="checkBtn" onclick="checkCode()">인증하기</button>
		<p class="codeError">&nbsp;</p>
	</div>
	
</div>