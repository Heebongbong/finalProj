<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set value="<%=request.getContextPath()%>" var="ctxPath" />
<c:set value="${sessionScope.LoginUser }" var="loginUser" />
<link rel="stylesheet" href="${ctxPath }/resources/css/slick/slick.css">
<link rel="stylesheet" href="${ctxPath }/resources/css/slick/slick-theme.css">
<script type="text/javascript" src="${ctxPath }/resources/js/slick/slick.js"></script>
<div id="list_wrap">
	<ul class="list_navi">
		<li class="list_navi_li <c:if test="${Category == '#요리'}">list_navi_li-active</c:if>">
			<a href="javascript:move_search_cate('#요리')">요리</a>
		</li>
		<li class="list_navi_li <c:if test="${Category == '#일상'}">list_navi_li-active</c:if>">
			<a href="javascript:move_search_cate('#일상')">일상</a>
		</li>
		<li class="list_navi_li <c:if test="${Category == '#캠핑'}">list_navi_li-active</c:if>">
			<a href="javascript:move_search_cate('#캠핑')">캠핑</a>
		</li>
		<li class="list_navi_li <c:if test="${Category == '#리뷰'}">list_navi_li-active</c:if>">
			<a href="javascript:move_search_cate('#리뷰')">리뷰</a>
		</li>
	</ul>
	<input type="hidden" value="${Category }" id="board_category">
	<!-- 신고 모달창 -->
	<div class="declaration_modal_overlay">
		<div class="decl_modal_header">
			<p>신고하기</p><span class="decl_modal_close" onclick="close_declaration()">X</span>
			<input type="hidden" class="decl_cm_no" value="">
			<input type="hidden" class="decl_nickname" value="">
		</div>
		<div class="decl_modal_body">
			<textarea rows="" cols="" class="decl_modal_text"></textarea>
			<input class="decl_modal_btn" type="button" value="제출하기" onclick="declaration()">
		</div>
	</div>
	<div class="list_main">
		
	</div>
</div>