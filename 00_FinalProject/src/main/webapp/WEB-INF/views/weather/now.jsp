<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
	<div id="map" style="width: 650px; height: 350px;"></div>
	
	<div class="weather_search">
		<input type="text" placeholder="날씨가 궁금한 지역을 입력하세요." id="locs" class="search_weather" value="${Addr }">
		<button type="button" onclick="moveWeather()"><i class="fa fa-search" aria-hidden="true"></i></button>
	</div>
	
	<div id="now_wrap">
		<c:if test="${!empty list }">
			<div>
				<%-- <h3>${list.get(0).getBaseDate() }/${list.get(0).getBaseTime() } 기준 측정 결과</h3> --%>
				<div class="weather_index_wrap">
					<div class="Timestamp"></div>
					<img class="icon_weather" alt="" src="${ctxPath }/resources/images/weather/icon_Weather.png">
					<img class="icon_temp" alt="" src="${ctxPath }/resources/images/weather/icon_Temp.png" style="margin-left: 3px; margin-right: 4px;">
					<img class="icon_rain" alt="" src="${ctxPath }/resources/images/weather/icon_Rain.png">
					<img class="icon_rmm" alt="" src="${ctxPath }/resources/images/weather/icon_Rmm.png">
					<img class="icon_stroke" alt="" src="${ctxPath }/resources/images/weather/icon_Stroke.png">
					<img class="icon_wind" alt="" src="${ctxPath }/resources/images/weather/icon_Wind.png">
				</div>
			<c:forEach items="${list }" var="dto">
			<c:set value="${dto.getCategory() }" var="cate"/>
				<div class="weather_wrap">
					<c:set var="fcstTime" value="${dto.getFcstTime()}" />
					<c:set var="substringTime" value="${fn:substring(fcstTime,0,2)}" />
					<h4>${substringTime}:00</h4>
					<div class="weather_info_wrap">
						<div class="weather_cate_info">
						  <c:choose>
						  	<c:when test="${cate.sky eq '1'}">
						  		<img class="day_style" alt="" src="${ctxPath }/resources/images/weather/day_Sunny1.png">
						  	</c:when>
			      			<c:when test="${cate.sky eq '3'}">
			      				<img class="day_style" alt="" src="${ctxPath }/resources/images/weather/day_Cloudy3.png">
			      			</c:when>
			      			<c:when test="${cate.sky eq '4'}">
			      				<img class="day_style" alt="" src="${ctxPath }/resources/images/weather/day_Bulr4.png">
			      			</c:when>
			      			<c:when test="${cate.pty eq '1'}">
			      				<img class="day_style" alt="" src="${ctxPath }/resources/images/weather/day_Rain1.png">
			      			</c:when>
			      			<c:when test="${cate.pty eq '2'}">
			      				<img class="day_style" alt="" src="${ctxPath }/resources/images/weather/day_RS2.png">
			      			</c:when>
			      			<c:when test="${cate.pty eq '3'}">
			      				<img class="day_style" alt="" src="${ctxPath }/resources/images/weather/day_Snow3.png">
			      			</c:when>
			      			<c:when test="${cate.pty eq '5'}">
			      				<img class="day_style" alt="" src="${ctxPath }/resources/images/weather/day_rain5.png">
			      			</c:when>
			      			<c:when test="${cate.pty eq '6'}">
								<img class="day_style" alt="" src="${ctxPath }/resources/images/weather/day_RS2.png">
							</c:when>
			      			<c:when test="${cate.pty eq '7'}">
			      				<img class="day_style" alt="" src="${ctxPath }/resources/images/weather/day_snow7.png">
			      			</c:when>
						  </c:choose>
						</div>
						<div class="weather_cate_info">
							${cate.t1h } ºC
						</div>
						<div class="weather_cate_info">
						  ${cate.reh } %
						</div>
						<div class="weather_cate_info">
							<c:if test="${cate.rn1 == '강수없음'}">0mm</c:if>
							<c:if test="${cate.rn1 != '강수없음'}">${cate.rn1 }</c:if>
						</div>
						<div class="weather_cate_info">
						  <c:choose>
							<c:when test="${cate.lgt eq '0'}">
						      <img alt="" src="${ctxPath }/resources/images/weather/Nstroke.png">
						    </c:when>
						    <c:otherwise>
						      <img alt="" src="${ctxPath }/resources/images/weather/Stroke.png">
						    </c:otherwise>
						  </c:choose>
						</div>
						<div class="weather_cate_info">
							${cate.wsd } m/s
							<img class="wind_degree_img" alt="" src="${ctxPath }/resources/images/weather/Vector.png" style="transform: rotate(${cate.vec}deg)">
						</div>
					</div>
				</div>
			</c:forEach>
			</div>
		</c:if>	
	</div>
</div>