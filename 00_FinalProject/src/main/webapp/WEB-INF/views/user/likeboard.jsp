<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="loginUser" value="${sessionScope.LoginUser}"/>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<link rel="stylesheet" href="${ctxPath }/resources/css/slick/slick.css">
<link rel="stylesheet" href="${ctxPath }/resources/css/slick/slick-theme.css">
<script type="text/javascript" src="${ctxPath }/resources/js/slick/slick.js"></script>
<div id="user_board_wrap">
	<div class="user_board_profile">
		<img class="user_profile_img" alt="" src="${loginUser.profile }">
		<div class="user_board_name_id">
			<span class="user_profile_nickname">
				${loginUser.nickname }
			</span>
			<span class="user_profile_email">
				${loginUser.email }
			</span>
		</div>
		<a href="${ctxPath }/user/mypage">마이페이지</a>
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
			<input class="decl_modal_btn" type="button" value="제출하기" onclick="declaration()">
		</div>
	</div>
	<div class="list_main">
	
	</div>
</div>