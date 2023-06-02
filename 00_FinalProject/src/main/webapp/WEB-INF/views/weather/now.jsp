<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<c:set value="${List }" var="list"/>
<c:set value="${Lat }" var="lat"/>
<c:set value="${Lng }" var="lng"/>
<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=4sqz6l4y8y&submodules=geocoder"></script>
<script type="text/javascript">
	let serv_lat = '${lat }';
	let serv_lng = '${lng }';
</script>
<div id="weather_wrap">
	<div id="map" style="width: 500px; height: 400px;"></div>
	<div id="weather_search">
		<input type="text" placeholder="지역명 입력" class="address" value="${Addr }">
		<input type="button" value="날씨 보기" onclick="moveWeather()">
	</div>
	<div id="now_wrap">
		<c:if test="${!empty list }">
				<div>
					<h3>${list.get(0).getBaseDate() }/${list.get(0).getBaseTime() } 기준 측정 결과</h3>
			<c:forEach items="${list }" var="dto">
			<c:set value="${dto.getCategory() }" var="cate"/>
				<div class="weather_wrap">
					<h4>${dto.getFcstDate() } / ${dto.getFcstTime() } 기준 날씨 예보</h4>
					<div class="weather_info_wrap">
						<div class="weather_cate_info">
							기온 : ${cate.t1h } `C
						</div>
						<div class="weather_cate_info">
							습도 : ${cate.reh } %
						</div>
						<div class="weather_cate_info">
							강수형태 : 
				      		<c:choose>
				      			<c:when test="${cate.pty eq '0'}">없음</c:when>
				      			<c:when test="${cate.pty eq '1'}">비</c:when>
				      			<c:when test="${cate.pty eq '2'}">비/눈</c:when>
				      			<c:when test="${cate.pty eq '3'}">눈</c:when>
				      			<c:when test="${cate.pty eq '5'}">빗방울</c:when>
				      			<c:when test="${cate.pty eq '6'}">빗방울눈날림</c:when>
				      			<c:when test="${cate.pty eq '7'}">눈날림</c:when>
				      		</c:choose>
						</div>
						<div class="weather_cate_info">
							강수량 : ${cate.rn1 } <c:if test="${cate.rn1 != '강수없음'}">mm</c:if>
						</div>
						<div class="weather_cate_info">
							하늘 상태 : 
							<c:choose>
				      			<c:when test="${cate.sky eq '1'}">맑음</c:when>
				      			<c:when test="${cate.sky eq '3'}">구름많음</c:when>
				      			<c:when test="${cate.sky eq '4'}">흐림</c:when>
				      		</c:choose>
						</div>
						<div class="weather_cate_info">
							낙뢰 : ${cate.lgt } kA
						</div>
						<div class="weather_cate_info">
							풍향 : ${cate.vec } deg
						</div>
						<div class="weather_cate_info">
							풍속 : ${cate.wsd } m/s
						</div>
						<div class="weather_cate_info">
							풍향(동서) : ${cate.uuu } m/s
						</div>
						<div class="weather_cate_info">
							풍향(북남) : ${cate.vvv } m/s
						</div>
					</div>
				</div>
			</c:forEach>
			</div>
		</c:if>	
	</div>
</div>