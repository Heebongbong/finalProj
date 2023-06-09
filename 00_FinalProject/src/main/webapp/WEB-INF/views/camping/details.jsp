<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<c:set value="${sessionScope.LoginUser }" var="loginUser"/>
<c:set var="camp_dto" value="${Content }" />
<script type="text/javascript">
	const campName = '${camp_dto.getFacltNm() }';
	const campLineIntro = '${camp_dto.getLineIntro() }';
</script>
<link rel="stylesheet" href="${ctxPath }/resources/css/slick/slick.css">
<link rel="stylesheet" href="${ctxPath }/resources/css/slick/slick-theme.css">
<script type="text/javascript" src="${ctxPath }/resources/js/slick/slick.js"></script>

<div id="camping_details_wrap">
	<input type="hidden" value="${camp_dto.getContent_id() }" id="hidden_content_id">
	
		<div id="camping_details_img">
			<img alt="" src="${camp_dto.getFirstImageUrl() }">
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
			<p class="camp_induty">${camp_dto.induty }</p>
			<p class="camp_facltNm">${camp_dto.facltNm }</p>
			<p class="camp_lctCl">${camp_dto.lctCl }</p>
			<p class="camp_addr1">${camp_dto.addr1 }</p>
			<p class="camp_tel">${camp_dto.tel }</p>
			<a class="camp_homepage" href="${camp_dto.homepage }">${camp_dto.homepage }</a>
			<hr>
			<p class="camp_lineIntro">${camp_dto.lineIntro }</p>
			<p class="camp_intro">${camp_dto.intro }</p>
			<div class="camp_tag">
				<p>
					<c:if test="${camp_dto.animalCmgCl eq 1}">#애견동반</c:if>
					<c:if test="${not empty camp_dto.posblFcltyCl}">#${camp_dto.posblFcltyCl}</c:if>
					<c:if test="${not empty camp_dto.exprnProgrm}">#${camp_dto.exprnProgrm}</c:if>
					<c:if test="${not empty camp_dto.themaEnvrnCl}">#${camp_dto.themaEnvrnCl}</c:if>
				</p>
			</div>
			<hr>
		</div>
	<div class="camping_review_wrap">
	</div>
</div>
