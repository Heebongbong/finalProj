<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="loginUser" value="${sessionScope.LoginUser}"/>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<c:set var="user_cont" value="${User_cont }"/>
<link rel="stylesheet" href="${ctxPath }/resources/css/slick/slick.css">
<link rel="stylesheet" href="${ctxPath }/resources/css/slick/slick-theme.css">
<script type="text/javascript" src="${ctxPath }/resources/js/slick/slick.js"></script>
<div id="user_board_wrap">
	<h2>${user_cont.nickname }의 게시글</h2>
	<input type="hidden" id="board_user_no" value="${user_cont.user_no }">
	<!-- 신고 모달창 -->
	<div class="declaration_modal_overlay">
		<div class="declaration_modal_window">
			<div class="decl_modal_header">
				<p class="decl_nickname"></p><span class="decl_modal_close" onclick="close_declaration()">X</span>
				<input type="hidden" class="decl_cm_no" value="">
			</div>
			<div class="decl_modal_body">
				<textarea rows="" cols="" class="decl_modal_text"></textarea>
			</div>
			<div class="decl_modal_btn">
				<input type="button" value="신고하기" onclick="declaration()">
			</div>
		</div>
	</div>
	<div class="list_main">
	
	</div>
</div>