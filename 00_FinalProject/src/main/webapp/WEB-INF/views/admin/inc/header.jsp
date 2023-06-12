<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="<%=request.getContextPath() %>" var="ctxPath"/>
<c:set value="${LoginUser }" var="loginUser"/>
<div id="header">
	<a href="${ctxPath }/admin">
		<img alt="" src="${ctxPath }/resources/images/logo/logo.png" width="50px" height="50px">
		관리자페이지
	</a>
	
	<c:if test="${empty loginUser }">
		<a href="${ctxPath }/admin/login">
			로그인
		</a>	
	</c:if>
	<c:if test="${!empty loginUser }">
		<a href="${ctxPath }/admin/logout">
			로그아웃
		</a>	
	</c:if>
</div>