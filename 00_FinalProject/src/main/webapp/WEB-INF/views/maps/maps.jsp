<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=4sqz6l4y8y&submodules=geocoder"></script>
<script type="text/javascript">const ctxPath = '${ctxPath }';</script>
<script type="text/javascript" src="${ctxPath }/resources/js/maps/maps.js"></script>
<style type="text/css">
	#container{
		text-align: center;
		width: 800px;
		margin: 100px auto;
	}
	#content{
		margin: 50px auto;
	}
	#map{
		margin: auto;
		width: 600px;
		height: 400px;
	}
</style>
<div id="content">
	<div id="map"></div>
	<input type="text" id="drive">
	<input type="button" value="드라이브 경로 추천" onclick="openwindows()">
	<p id="addr"></p>
</div>