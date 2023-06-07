<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="<%=request.getContextPath() %>" var="ctxPath"/>
<div id="weather_footer">
	<ul class="move_navi">
		<li><a class="navi_footer_a" href="${ctxPath }/weather/stella"><img alt="" src="${ctxPath }/resources/images/icon/star_icon.png"><span>별자리</span></a></li>
		<li><a class="navi_footer_a" href="${ctxPath }/weather/star"><img alt="" src="${ctxPath }/resources/images/icon/cloud_icon.png"><span>구름양</span></a></li>
		<li><a class="navi_footer_a" href="${ctxPath }/indexNavi"><img alt="" src="${ctxPath }/resources/images/icon/home_icon.png"><span>홈</span></a></li>
		<li><a class="navi_footer_a" href="${ctxPath }/weather/now"><img alt="" src="${ctxPath }/resources/images/icon/weather_icon.png"><span>날씨</span></a></li>
		<li><a class="navi_footer_a" href="${ctxPath }/driveNavi"><img alt="" src="${ctxPath }/resources/images/icon/drive_icon.png"><span>드라이브</span></a></li>
	</ul>
</div>