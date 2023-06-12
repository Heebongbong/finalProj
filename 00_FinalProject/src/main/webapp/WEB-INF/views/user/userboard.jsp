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
	<div class="user_board_profile">
		<img class="user_profile_img" alt="" src="${user_cont.profile }">
		<div class="user_board_name_id">
			<span class="user_profile_nickname">
				${user_cont.nickname }
			</span>
			<span class="user_profile_email">
				${user_cont.email }
			</span>
		</div>
		<c:if test="${loginUser.user_no == user_cont.user_no }">
		<a href="${ctxPath }/user/mypage"><img class="mypage_icon" src="${ctxPath }/resources/images/icon/option.png"></a>
		</c:if>
		<c:if test="${loginUser.user_no != user_cont.user_no }">
			<img class="user_profile_chat_plus" onclick="chat_board(${user_cont.user_no })" alt="" src="${ctxPath }/resources/images/icon/chat_plus_icon.png">
		</c:if>
	</div>
	<input type="hidden" id="board_user_no" value="${user_cont.user_no }">
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