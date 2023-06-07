<%@page import="java.util.ArrayList"%>
<%@page import="com.spring.finproj.model.camping.CampingDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<c:set value="${sessionScope.LoginUser }" var="loginUser"/>
<c:set var="dto" value="${Content }" />
<script type="text/javascript">
	const campName = '${dto.getFacltNm() }';
	const campLineIntro = '${dto.getLineIntro() }';
	const campingX = '${dto.getMapX() }';
	const campingY = '${dto.getMapY() }';
</script>
<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=4sqz6l4y8y&submodules=geocoder"></script>
<link rel="stylesheet" href="${ctxPath }/resources/css/slick/slick.css">
<link rel="stylesheet" href="${ctxPath }/resources/css/slick/slick-theme.css">
<script type="text/javascript" src="${ctxPath }/resources/js/slick/slick.js"></script>
<div id="map_wrap">
	<input type="hidden" value="${dto.getContent_id() }" id="hidden_content_id">
	<div>
		<input type="button" value="리뷰작성" onclick="review_write_move(${dto.getContent_id() }, ${loginUser.isAuthen() })">
	</div>
	<div id="camping_details_wrap">
		<div id="camping_details_img">
			<div id="map" style="width:400px; height: 50%;"></div>
			<img alt="" src="${dto.getFirstImageUrl() }" width="400px" height="50%">
		</div>
		<!-- 신고 모달창 -->
		<div class="declaration_modal_overlay">
			<div class="decl_modal_header">
				<p>신고하기</p><span class="decl_modal_close" onclick="close_declaration()">X</span>
				<input type="hidden" class="decl_cm_no" value="">
				<input type="hidden" class="decl_nickname" value="">
			</div>
			<div class="decl_modal_body">
				<textarea rows="" cols="" class="decl_modal_text"></textarea>
				<input class="decl_modal_btn" type="button" value="제출하기" onkeydown="if( event.keyCode == 13 ){declaration();}" onclick="declaration()">
			</div>
		</div>
		<div id="camping_details">
			<table>
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
					<td> <a href="${dto.getHomepage() }" target="_blank">${dto.getHomepage() }</a></td>
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
        		<tr>
					<th>아이디</th>
					<td>${dto.getContent_id() }</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="camping_review_wrap">
	
	</div>
</div>