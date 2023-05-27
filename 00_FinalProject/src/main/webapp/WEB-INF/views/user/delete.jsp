<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="loginUser" value="${sessionScope.LoginUser}"/>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<c:set var="dto" value="${content }"/>
<div class="drop_wrap">
	<h2>회원 탈퇴</h2>
	<form id="forgetForm" action="${ctxPath }/user/deleteOk" method="post">
		<div>
			<p>비밀번호 입력</p>
			<input type="password" name="check_pwd" id="check_pwd">
		</div>
		<button type="submit" id="submitBtn" class="noWhitespace">탈퇴하기</button>
	</form>
</div>