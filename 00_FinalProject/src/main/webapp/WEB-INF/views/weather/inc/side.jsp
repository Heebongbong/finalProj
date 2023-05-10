<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<div id="weather_side">
	<ul>
		<li>
			<a href="${ctxPath }/weather/content">디테일</a>
		</li>
		<li>
			<a href="${ctxPath }/weather/now">실시간</a>
		</li>
		<li>
			<a href="${ctxPath }/weather/star">별자리</a>
		</li>
		<li>
			<a href="${ctxPath }/weather/stella">Stella</a>
		</li>
	</ul>
</div>