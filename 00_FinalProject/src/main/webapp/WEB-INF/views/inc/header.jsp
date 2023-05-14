<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="<%=request.getContextPath() %>" var="ctxPath"/>
<c:set value="${sessionScope.LoginUser }" var="loginUser"/>
<div id="header">
	<ul id="login_navi">
		<c:if test="${empty loginUser }">
			<li><a href="${ctxPath }/loginNavi">로그인</a></li>
		</c:if>
		<c:if test="${!empty loginUser }">
			<li>${loginUser.getUser_nickname() }님 환영합니다.</li>
			<li><a href="${ctxPath }/login/logout">로그아웃</a></li>
		</c:if>
	</ul>
	<ul id="move_navi">
		<li><a href="${ctxPath }/indexNavi">홈</a></li>
		<li><a href="${ctxPath }/campingNavi">캠핑장</a></li>
		<li><a href="${ctxPath }/weatherNavi">날씨</a></li>
		<li><a href="${ctxPath }/driveNavi">드라이브</a></li>
		<li><a href="${ctxPath }/boardNavi">커뮤니티</a></li>
		<li><a href="${ctxPath }/marketNavi">중고거래</a></li>
	</ul>
</div>