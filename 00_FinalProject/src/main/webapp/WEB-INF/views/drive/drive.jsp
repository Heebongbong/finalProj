<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=4sqz6l4y8y&submodules=geocoder"></script>
<div id="content">
	<div id="map" style="height: 500px;"></div>
	<input type="text" id="drive">
	<input type="button" value="드라이브 경로 추천" onclick="openwindows()">
	<p id="addr"></p>

	<div id="view">
	</div>

	<div>
		${GpsDTO }
	</div>
</div>