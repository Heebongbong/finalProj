<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="<%=request.getContextPath() %>" var="ctxPath"/>
<c:set value="${sessionScope.LoginUser }" var="loginUser"/>
<c:set value="${CampingList }" var="campingList" />
<c:set value="${BoardList }" var="boardList" />
<link rel="stylesheet" href="${ctxPath }/resources/css/slick/slick.css">
<link rel="stylesheet" href="${ctxPath }/resources/css/slick/slick-theme.css">
<script type="text/javascript" src="${ctxPath }/resources/js/slick/slick.js"></script>
<div id="index_wrap">
	<c:if test="${!empty campingList }">
		<div class="index_camping_list slider-for">
       		 <c:forEach items="${campingList}" var="dto">
				<div class="index_camping_content">
					<div class="camping_img">
						<img alt="${dto.getFacltNm() } 캠핑장 대표 사진" src="${dto.firstImageUrl }" />
					</div>
					<div class="camping_name">
						<a onclick="location.href='${ctxPath }/camping/details?content_id=${dto.getContent_id() }'" href="javascript:">${dto.getFacltNm() }</a>
					</div>
				</div>
			</c:forEach>
		</div>
	</c:if>
	<c:if test="${empty campingList }"><h2>캠핑 조회 중 오류</h2></c:if>
	<div class="banner">
		<img alt="" src="${ctxPath }/resources/images/banner/${banner_num }.jpg">
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
	<div class="index_board_wrap">
		
	</div>
</div>