<%@page import="java.util.ArrayList"%>
<%@page import="com.spring.finproj.model.camping.CampingDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
@SuppressWarnings("all")
ArrayList<CampingDTO> list = (ArrayList<CampingDTO>)request.getAttribute("CampingList");
%>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=4sqz6l4y8y&submodules=geocoder"></script>
<div id="map_wrap">
	<div id="map" style="height: 1000px;"></div>
	<div id="camping_details_wrap">
		<c:set var="dto" value="${Content }" />
		<div id="camping_details">
				<p>이름: ${dto.getFacltNm() }</p>
				<p>소개: ${dto.getLineIntro() }</p>
				<p>홈페이지: ${dto.getHomepage() }</p>
				<p>위도: ${dto.getMapY() }</p>
				<p>경도: ${dto.getMapX() }</p>
				<p>id: ${dto.getContent_id() }</p>
				
				
			<img alt="" src="${dto.getFirstImageUrl() }">
			<p>: ${dto.getContent_id() }</p>
        	<p>: ${dto.getFacltNm() }</p>
        	<p>: ${dto.getLineIntro() }</p>
        	<p>: ${dto.getIntro() }</p>
        	<p>: ${dto.getAllar() }</p>
        	<p>: ${dto.getFeatureNm() }</p>
        	<p>: ${dto.getInduty() }</p>
        	<p>: ${dto.getLctCl() }</p>
        	<p>: ${dto.getAddr1() }</p>
        	<p>: ${dto.getAddr2() }</p>
        	<p>: ${dto.getMapX() }</p>
        	<p>: ${dto.getMapY() }</p>
        	<p>: ${dto.getTooltip() }</p>
        	<p>: ${dto.getTel() }</p>
        	<p>: ${dto.getHomepage() }</p>
        	<p>: ${dto.getOperPdCl() }</p>
        	<p>: ${dto.getOperDeCl() }</p>
        	<p>: ${dto.getPosblFcltyCl() }</p>
        	<p>: ${dto.getExprnProgrm() }</p>
        	<p>: ${dto.getThemaEnvrnCl() }</p>
        	<c:if test="${dto.getAnimalCmgCl() == 0 }">
        		<p>예견동반: X
        	</c:if>
        	<c:if test="${dto.getAnimalCmgCl() != 0 }">
        		<p>예견동반: O
        	</c:if>
		</div>
	</div>
	
</div>

<script type="text/javascript">

const popup = document.querySelector('.url');
const ctxPath = "${ctxPath}";
const content_id = "${dto.getContent_id()}";

popup.addEventListener("click", function() {
	window.open("camping/details?content_id="+content_id, "팝업1", "width=400, height=560, scrollbars=yes");
});

/* function popup(){
   	let url = "camping.camp";
    let name = "popup";
    let option = "width = 200, height = 200, top = 100, left = 200, location = yes, scrollbars = yes"
    window.open(url, name, option);
} */
		
	

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