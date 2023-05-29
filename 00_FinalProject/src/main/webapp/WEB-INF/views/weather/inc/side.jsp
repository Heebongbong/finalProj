<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<div id="weather_side">
	<ul>
		<li>
			<a href="${ctxPath }/weather/now">실시간 날씨 및 예보</a>
		</li>
		<li>
			<a href="${ctxPath }/weather/star">실시간 위성사진</a>
		</li>
		<li>
			<a href="${ctxPath }/weather/stella">Stella/별자리 어플</a>
		</li>
	</ul>
</div>