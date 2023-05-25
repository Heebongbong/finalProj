<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="<%=request.getContextPath() %>" var="ctxPath"/>
<div id="footer">
	<ul class="move_navi">
		<li class="navi_camping"><a href="${ctxPath }/camping/content"><img alt="" src="${ctxPath }/resources/images/logo/tent.png"></a></li>
		<li><a href="${ctxPath }/weatherNavi">날씨</a></li>
		<li><a href="${ctxPath }/driveNavi">드라이브</a></li>
		<li><a href="${ctxPath }/boardNavi">커뮤니티</a></li>
		<li><a href="${ctxPath }/marketNavi">중고거래</a></li>
	</ul>
</div>