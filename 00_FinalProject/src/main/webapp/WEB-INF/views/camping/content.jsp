<%@page import="java.util.ArrayList"%>
<%@page import="com.spring.finproj.model.camping.CampingDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
@SuppressWarnings("all")
ArrayList<CampingDTO> list = (ArrayList<CampingDTO>)request.getAttribute("campingList");
%>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=4sqz6l4y8y&submodules=geocoder"></script>
<div id="map_wrap">
	<div id="map" style="height: 1000px;"></div>
	<div id="test_wrap">
		<c:forEach items="${campingList }" var="dto">
		<div id="test">
			<div id="test_image">
				<img alt="" src="${dto.getImg() }">
			</div>
			<div id="test_info">
				<p>이름: ${dto.getName() }</p>
				<p>소개: ${dto.getIntro() }</p>
				<p>홈페이지: ${dto.getHomePage() }</p>
				<p>위도: ${dto.getY() }</p>
				<p>경도: ${dto.getX() }</p>
			</div>
		</div>
		</c:forEach>
	</div>
	
	<div>
		${campings }
	</div>
</div>

<script type="text/javascript">
		$(document).ready(function(){
			let mapOptions = {
			    center: new naver.maps.LatLng(37.567944413725904,126.9831230334937),
			    zoom: 6
			};
			
			let arrayinfo = new Array();
			arrayinfo.push(
					<%for(CampingDTO d : list){ %>
					{name: "<%=d.getFacltNm() %>", intro: "<%=d.getIntro() %>", lat: "<%=d.getMapY() %>", lng: "<%=d.getMapX() %>"},
					<%}%>
			);
			
			let map = new naver.maps.Map('map', mapOptions);
			
			let markers = new Array();
			let infos = new Array();
			
			for(let i=0;i<arrayinfo.length;i++){
				let marker = new naver.maps.Marker({
				    title: arrayinfo[i].name,
					position: new naver.maps.LatLng(arrayinfo[i].lat, arrayinfo[i].lng),
				    map: map
				});
				
				let infoWindow = new naver.maps.InfoWindow({
					content: "<div style='width: 200px;test-align:center;padding:10px;'><b>"+arrayinfo[i].name+"</b><br><font>"+arrayinfo[i].intro+"</font></div>"
				});
				
				markers.push(marker);
				infos.push(infoWindow);
			}
			
			function getClickHandler(seq) {
				return function(e) {
					let marker = markers[seq],
					infoWindow = infos[seq];
					
					if(infoWindow.getMap()){
						infoWindow.close();
					}else{
						infoWindow.open(map, marker);
					}
				}
			}
			
			for(let i=0;i<markers.length;i++){
				naver.maps.Event.addListener(markers[i], 'click', getClickHandler(i));
			}
			
		});
</script>