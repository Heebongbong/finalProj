<%@page import="java.util.ArrayList"%>
<%@page import="com.spring.finproj.model.camping.CampingDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<c:set var="dto" value="${Content }" />
<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=4sqz6l4y8y&submodules=geocoder"></script>
<div id="map_wrap">
	<div id="camping_details_wrap">
		
		<div id="camping_details_img">
			<div id="map" style="width:400px; height: 50%;"></div>
			<img alt="" src="${dto.getFirstImageUrl() }" width="400px" height="50%">
		</div>
		<div id="camping_details">
			<table border="1" cellspacing="1" >
				<tr>
					<th>이름</th>
					<td>${dto.getFacltNm() }</td>
				</tr>
				<tr>
					<th>야영장명</th>
					<td> ${dto.getFacltNm() }</td>
				</tr>
				<tr>
					<th>전체면적</th>
					<td>${dto.getAllar() }</td>
				</tr>
				<tr>
					<th>업종</th>
					<td>${dto.getInduty() }</td>
				</tr>
				<tr>
					<th>한줄소개</th>
					<td>${dto.getLineIntro() }</td>
				</tr>
				<tr>
					<th>소개</th>
					<td>${dto.getIntro() }</td>
				</tr>
				
				
				
				<tr>
					<th>소개</th>
					<td>${dto.getLineIntro() }</td>
				</tr>
				<tr>
					<th>홈페이지</th>
					<td> ${dto.getHomepage() }</td>
				</tr>
				<tr>
					<th>위도</th>
					<td>${dto.getMapY() }</td>
				</tr>
				<tr>
					<th>경도</th>
					<td>${dto.getMapX() }</td>
				</tr>
				
				
				
				<tr>
					<th>특징</th>
					<td>${dto.getFeatureNm() }</td>
				</tr>
				
				<tr>
					<th>입지구분</th>
					<td>${dto.getLctCl() }</td>
				</tr>
				<tr>
					<th>주소</th>
					<td>${dto.getAddr1() }</td>
				</tr>
				<tr>
					<th>주소상세</th>
					<td>${dto.getAddr2() }</td>
				</tr>
				<tr>
					<th>툴팁</th>
					<td>${dto.getTooltip() }</td>
				</tr>
				<tr>
					<th>연락처</th>
					<td>${dto.getTel() }</td>
				</tr>
				<tr>
					<th>운영기간</th>
					<td>${dto.getOperPdCl() }</td>
				</tr>
				<tr>
					<th>운영일</th>
					<td>${dto.getOperDeCl() }</td>
				</tr>
				<tr>
					<th>주변<br>이용가능시설</th>
					<td>${dto.getPosblFcltyCl() }</td>
				</tr>
				<tr>
					<th>체험<br>프로그램명</th>
					<td>${dto.getExprnProgrm() }</td>
				</tr>
				<tr>
					<th>테마환경</th>
					<td>${dto.getThemaEnvrnCl() }</td>
				</tr>
				
				<tr>
					<c:if test="${dto.getAnimalCmgCl() == 0 }">
						<th>애견동반</th>
		        		<td>X</td>
		        	</c:if>
		        	<c:if test="${dto.getAnimalCmgCl() != 0 }">
		        		<th>애견동반</th>
		        		<td>O</td>
		        	</c:if>
        		</tr>
			</table>	
		</div>
	</div>
</div>

<script type="text/javascript">

$(document).ready(function(){
	let mapOptions = {
	    center: new naver.maps.LatLng(${dto.getMapY() },${dto.getMapX() }),
	    zoom: 15
	};
	
	
	let map = new naver.maps.Map('map', mapOptions);
	
	
	let marker = new naver.maps.Marker({
	    title: "${dto.getFacltNm() }",
		position: new naver.maps.LatLng(${dto.getMapY() }, ${dto.getMapX() }),
	    map: map
	});
	
	let infoWindow = new naver.maps.InfoWindow({
		content: "<div style='width: 200px;test-align:center;padding:10px;'><b>${dto.getFacltNm() }</b><br><font>${dto.getLineIntro() }</font></div>"
	});
	
	function getClickHandler() {
		return function(e) {
			if(infoWindow.getMap()){
				infoWindow.close();
			}else{
				infoWindow.open(map, marker);
			}
		}
	}
	
	naver.maps.Event.addListener(marker, 'click', getClickHandler());
	
});
		
</script>