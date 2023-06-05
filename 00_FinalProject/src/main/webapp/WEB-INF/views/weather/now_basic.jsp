<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=240232676b5466ea05842dea75a09f75&libraries=services"></script>

<div id="weather_wrap">
	<div id="weather_search">
		<input type="text" placeholder="지역명 입력" class="address">
		<input type="button" value="날씨 보기" onclick="moveWeather()">
	</div>
	
	<div id="map" style="width: 700px; height: 400px; display: none"></div>
		
	
	
	<div id="now_wrap">
		<table>
			<tr>
				<th colspan="2">현재날씨(${str['bastime']})</th>
			</tr>
		  	<tr>
				<td>카테고리</td>
				<td>측정값</td>
		 	</tr>
		  	<c:forEach var="item" items="${str['category']}" varStatus="loop">
			    <tr>
					<td>
						<c:choose>
				        	<c:when test="${item eq 'T1H'}">기온(℃)</c:when>
					        <c:when test="${item eq 'RN1'}">1시간 강수량(mm)</c:when>
					        <c:when test="${item eq 'UUU'}">동서바람성분(m/s)</c:when>
					        <c:when test="${item eq 'VVV'}">남북바람성분(m/s)</c:when>
					        <c:when test="${item eq 'REH'}">습도(%)</c:when>
					        <c:when test="${item eq 'PTY'}">강수형태</c:when>
					        <c:when test="${item eq 'VEC'}">풍향(deg)</c:when>
					        <c:when test="${item eq 'WSD'}">풍속(m/s)</c:when>
					    </c:choose>
			      	</td>
			      	
			      	<td>
				      	<c:choose>
							<c:when test="${str['obsrValue'][loop.index] >= 900 || str['obsrValue'][loop.index] <= -900}">자료 x</c:when>
							
			      			<c:when test="${item eq 'PTY' && str['obsrValue'][loop.index] == 0}">없음</c:when>
			      			<c:when test="${item eq 'PTY' && str['obsrValue'][loop.index] == 1}">비</c:when>
			      			<c:when test="${item eq 'PTY' && str['obsrValue'][loop.index] == 2}">비/눈</c:when>
			      			<c:when test="${item eq 'PTY' && str['obsrValue'][loop.index] == 3}">눈</c:when>
			      			<c:when test="${item eq 'PTY' && str['obsrValue'][loop.index] == 5}">빗방울</c:when>
			      			<c:when test="${item eq 'PTY' && str['obsrValue'][loop.index] == 6}">빗방울눈날림</c:when>
			      			<c:when test="${item eq 'PTY' && str['obsrValue'][loop.index] == 7}">눈날림</c:when>

			      			<c:when test="${item eq 'VEC' && (str['obsrValue'][loop.index] >= 0 && str['obsrValue'][loop.index] < 45)}">${str['obsrValue'][loop.index]}(N-NE)</c:when>
			      			<c:when test="${item eq 'VEC' && (str['obsrValue'][loop.index] >= 45 && str['obsrValue'][loop.index] < 90)}">${str['obsrValue'][loop.index]}(NE-N)</c:when>
			      			<c:when test="${item eq 'VEC' && (str['obsrValue'][loop.index] >= 90 && str['obsrValue'][loop.index] < 135)}">${str['obsrValue'][loop.index]}(E-SE)</c:when>
			      			<c:when test="${item eq 'VEC' && (str['obsrValue'][loop.index] >= 135 && str['obsrValue'][loop.index] < 180)}">${str['obsrValue'][loop.index]}(SE-E)</c:when>
			      			<c:when test="${item eq 'VEC' && (str['obsrValue'][loop.index] >= 180 && str['obsrValue'][loop.index] < 225)}">${str['obsrValue'][loop.index]}(S-SW)</c:when>
			      			<c:when test="${item eq 'VEC' && (str['obsrValue'][loop.index] >= 225 && str['obsrValue'][loop.index] < 270)}">${str['obsrValue'][loop.index]}(SW-W)</c:when>
			      			<c:when test="${item eq 'VEC' && (str['obsrValue'][loop.index] >= 270 && str['obsrValue'][loop.index] < 315)}">${str['obsrValue'][loop.index]}(W-NW)</c:when>
			      			<c:when test="${item eq 'VEC' && (str['obsrValue'][loop.index] >= 315 && str['obsrValue'][loop.index] < 360)}">${str['obsrValue'][loop.index]}(NW-N)</c:when>
			      			
			      			<c:when test="${item eq 'WSD' && str['obsrValue'][loop.index] < 4}">${str['obsrValue'][loop.index]}(약한바람)</c:when>
			      			<c:when test="${item eq 'WSD' && (str['obsrValue'][loop.index] >= 4 && str['obsrValue'][loop.index] < 9)}">${str['obsrValue'][loop.index]}(약간강한바람)</c:when>
			      			<c:when test="${item eq 'WSD' && (str['obsrValue'][loop.index] >= 9 && str['obsrValue'][loop.index] < 14)}">${str['obsrValue'][loop.index]}(강한바람)</c:when>
			      			<c:when test="${item eq 'WSD' && str['obsrValue'][loop.index] > 14}">${str['obsrValue'][loop.index]}(매우강한바람)</c:when>
			      			<c:when test="${item eq 'WSD' && str['obsrValue'][loop.index] > 14}">${str['obsrValue'][loop.index]}(매우강한바람)</c:when>
			      			
			      			
			      			<c:otherwise>${str['obsrValue'][loop.index]}</c:otherwise>
						</c:choose>
					</td>
			    </tr>
		 	</c:forEach>
		</table>
		
		<table>
			<tr>
				<th colspan="3">6시간 후 날씨</th>
			</tr>
		  	<tr>
		  		<td>시간</td>
				<td>카테고리</td>
				<td>측정값</td>
		 	</tr>
		  	<c:forEach var="item2" items="${str2['fcstCategory']}" varStatus="loop">
			    <tr>
			    	<!-- 6시간 후만 쓰려면 아래 코드 추가해야함 -->
			    	 <%-- <c:if test="${loop.index % 6 == 5}"> --%>
				    	<td>
							<c:choose>
								<c:when test="${str2['fcstTime'][loop.index].endsWith('00')}">
							    	${str2['fcstTime'][loop.index].substring(0, str2['fcstTime'][loop.index].length() - 2)}시
								</c:when>
								<c:otherwise>
								      ${str2['fcstTime'][loop.index]}시
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:choose>
					        	<c:when test="${item2 eq 'T1H'}">기온(℃)</c:when>
						        <c:when test="${item2 eq 'RN1'}">1시간 강수량(mm)</c:when>
						        <c:when test="${item2 eq 'UUU'}">동서바람성분(m/s)</c:when>
						        <c:when test="${item2 eq 'VVV'}">남북바람성분(m/s)</c:when>
						        <c:when test="${item2 eq 'REH'}">습도(%)</c:when>
						        <c:when test="${item2 eq 'PTY'}">강수형태</c:when>
						        <c:when test="${item2 eq 'VEC'}">풍향(deg)</c:when>
						        <c:when test="${item2 eq 'WSD'}">풍속(m/s)</c:when>
						        <c:when test="${item2 eq 'SKY'}">하늘상태</c:when>
						        <c:when test="${item2 eq 'LGT'}">낙뢰(kA)</c:when>
						    </c:choose>
				      	</td>
				      	
				      	<td> 
				      		<c:choose>
				      			<c:when test="${item2 eq 'SKY' && str2['fcstValue'][loop.index] eq '1'}">맑음</c:when>
				      			<c:when test="${item2 eq 'SKY' && str2['fcstValue'][loop.index] eq '3'}">구름많음</c:when>
				      			<c:when test="${item2 eq 'SKY' && str2['fcstValue'][loop.index] eq '4'}">흐림</c:when>
				      			
				      			<c:when test="${item2 eq 'PTY' && str2['fcstValue'][loop.index] eq '0'}">없음</c:when>
				      			<c:when test="${item2 eq 'PTY' && str2['fcstValue'][loop.index] eq '1'}">비</c:when>
				      			<c:when test="${item2 eq 'PTY' && str2['fcstValue'][loop.index] eq '2'}">비/눈</c:when>
				      			<c:when test="${item2 eq 'PTY' && str2['fcstValue'][loop.index] eq '3'}">눈</c:when>
				      			<c:when test="${item2 eq 'PTY' && str2['fcstValue'][loop.index] eq '5'}">빗방울</c:when>
				      			<c:when test="${item2 eq 'PTY' && str2['fcstValue'][loop.index] eq '6'}">빗방울눈날림</c:when>
				      			<c:when test="${item2 eq 'PTY' && str2['fcstValue'][loop.index] eq '7'}">눈날림</c:when>
				      			
				      			<c:otherwise>${str2['fcstValue'][loop.index]}</c:otherwise>
				      		</c:choose>
				      	</td>
			      	<%-- </c:if> --%>
			    </tr>
		 	</c:forEach>
		</table>
	</div>
	
</div>

