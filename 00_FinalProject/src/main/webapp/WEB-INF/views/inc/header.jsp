<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="<%=request.getContextPath() %>" var="ctxPath"/>
<div id="header">
	<ul>
		<li><a href="${ctxPath }/indexNavi">홈</a></li>
		<li><a href="${ctxPath }/campingNavi">캠핑장</a></li>
		<li><a href="${ctxPath }/weatherNavi">날씨</a></li>
		<li><a href="${ctxPath }/driveNavi">드라이브</a></li>
		<li><a href="${ctxPath }/boardNavi">커뮤니티</a></li>
		<li><a href="${ctxPath }/marketNavi">중고거래</a></li>
	</ul>
</div>