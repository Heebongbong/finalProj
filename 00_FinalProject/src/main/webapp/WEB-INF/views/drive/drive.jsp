<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=4sqz6l4y8y&submodules=geocoder"></script>
<div id="drive_wrap">
	<div id="map" style="height: 400px;"></div>

	<div id="drive_main_wrap">
		<div class="inp_drive">
			<span class="inp_placeholder">출발지 :</span><input type="text" id="start_drive" value="">
			<span class="inp_placeholder">도착지 :</span><input type="text" id="drive">
			<span>※ 미 입력시 기본 (서울특별시 중구 남대문로1가 18) </span>
			<input type="button" id="search" value="드라이브 경로 추천" onclick="openwindows()">
			<p id="addr"></p>
		</div>
	</div>
	
	<div id="drive_road_view">
	</div>
</div>