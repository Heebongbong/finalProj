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
<script>
	let loginUser_no = '${loginUser.user_no }';
	let loginUser_profile = '${loginUser.profile }';
</script>
<div id="index_wrap">
	<c:if test="${!empty campingList }">
		<div class="index_camping_list slider-for">
       		 <c:forEach items="${campingList}" var="dto">
				<div class="index_camping_content">
					<div class="camping_img">
						<img style="width: 800px; height: 400px;" alt="" src="${dto.firstImageUrl }" />
					</div>
					<div class="camping_name">
					 	<span class="promotion_font">${dto.facltNm}</span>
					</div>
					<div class="camping_info">
					 	<span class="promotion_font">${dto.lineIntro}</span>
					</div>
				</div>
			</c:forEach>
		</div>
	</c:if>
	<c:if test="${empty campingList }"><h2>캠핑 조회 중 오류</h2></c:if>
	<div class="banner">
		<img alt="" src="${ctxPath }/resources/images/banner/${banner_num }.jpg">
	</div>
<script type="text/javascript">
	$(document).ready(function(){
		$(".board_main_files").slick({
			dots: true,
			infinite: true,
			speed: 1000,
			slidesToShow: 1,
			slidesToScroll: 1,
			adaptiveHeight: true,
			arrows: false,
			draggable: true
		});
	});
</script>
	<div class="index_board_wrap">
		
	</div>
</div>