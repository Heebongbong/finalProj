<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=240232676b5466ea05842dea75a09f75&libraries=services"></script>
<div id="weather_wrap">
	<div>
		<input type="text" placeholder="지역 입력" class="address">
		<input type="button" value="날씨 보기" onclick="moveWeather()">
	</div>
	<div id="map" style="width: 500px; height: 400px; display: none"></div>
</div>
