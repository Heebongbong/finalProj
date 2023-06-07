<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="<%=request.getContextPath() %>" var="ctxPath"/>
<c:set value="${sessionScope.LoginUser }" var="loginUser"/>
<link rel="stylesheet" href="${ctxPath }/resources/css/slick/slick.css">
<link rel="stylesheet" href="${ctxPath }/resources/css/slick/slick-theme.css">
<script type="text/javascript" src="${ctxPath }/resources/js/slick/slick.js"></script>
<div id="market_wrap">
	<div class="market_navi">
		<div class="market_navi_cate">
			<a>상품별</a>
			<a>지역별</a>
		</div>
		<div class="market_navi_cont">
			<ul class="obj_ul">
				<li class="obj_li <c:if test="${empty Category }">li-active</c:if>">
					<a href="${ctxPath }/market/list">전체</a>
				</li>
				<li class="obj_li <c:if test="${Category == '#텐트#타프'}">li-active</c:if>">
					<a href="javascript:move_search_cate('#텐트#타프')">텐트/타프</a>
				</li>
				<li class="obj_li <c:if test="${Category == '#테이블#체어'}">li-active</c:if>">
					<a href="javascript:move_search_cate('#테이블#체어')">테이블/체어</a>
				</li>
				<li class="obj_li <c:if test="${Category == '#취사#수납'}">li-active</c:if>">
					<a href="javascript:move_search_cate('#취사#수납')">취사/수납</a>
				</li>
				<li class="obj_li <c:if test="${Category == '#랜턴#조명'}">li-active</c:if>">
					<a href="javascript:move_search_cate('#랜턴#조명')">랜턴/조명</a>
				</li>
				<li class="obj_li <c:if test="${Category == '#소품#기타'}">li-active</c:if>">
					<a href="javascript:move_search_cate('#소품#기타')">소품/기타</a>
				</li>
			</ul>
			<ul class="loc_ul">
				<li class="loc_li <c:if test="${Category == '#서울'}">li-active</c:if>">
					<a href="javascript:move_search_cate('#서울')">서울시</a>
				</li>
				<li class="loc_li <c:if test="${Category == '#경기'}">li-active</c:if>">
					<a href="javascript:move_search_cate('#경기')">경기도</a>
				</li>
				<li class="loc_li <c:if test="${Category == '#강원'}">li-active</c:if>">
					<a href="javascript:move_search_cate('#강원')">강원도</a>
				</li>
				<li class="loc_li <c:if test="${Category == '#충청'}">li-active</c:if>">
					<a href="javascript:move_search_cate('#충청')">충청도</a>
				</li>
				<li class="loc_li <c:if test="${Category == '#전라'}">li-active</c:if>">
					<a href="javascript:move_search_cate('#전라')">전라도</a>
				</li>
				<li class="loc_li <c:if test="${Category == '#경상'}">li-active</c:if>">
					<a href="javascript:move_search_cate('#경상')">경상도</a>					
				</li>
				<li class="loc_li <c:if test="${Category == '#제주'}">li-active</c:if>">
					<a href="javascript:move_search_cate('#제주')">제주도</a>
				</li>
			</ul>
		</div>
	</div>
	<form class="market_search" action="" method="get">
		<input type="text" value="${M_Keyword }" id="market_keyword" placeholder="중고거래 검색" name="keyword">
		<button type="submit"><i class="fa fa-search" aria-hidden="true"></i></button>
	</form>
	<input type="hidden" value="${Category }" id="market_category">
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
	<div class="market_main">
		
	</div>
</div>