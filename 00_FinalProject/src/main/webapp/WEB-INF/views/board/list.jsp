<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set value="<%=request.getContextPath()%>" var="ctxPath" />
<c:set value="${sessionScope.LoginUser }" var="loginUser" />
<c:set value="${BoardList }" var="boardList" />
<link rel="stylesheet" href="${ctxPath }/resources/css/slick/slick.css">
<link rel="stylesheet" href="${ctxPath }/resources/css/slick/slick-theme.css">
<script type="text/javascript" src="${ctxPath }/resources/js/slick/slick.js"></script>
<script>
	let loginUser_no = '${loginUser.user_no }';
	let loginUser_profile = '${loginUser.profile }';
	
	function move_search_cate(key){
		let keyword = $('#board_keyword').val();
		location.href=ctxPath+"/board/list?keyword=%23"+key+"%23"+keyword;
	}
</script>
<div id="list_wrap">
	<ul class="list_navi">
		<li class="list_navi_li">
			<a href="javascript:move_search_cate('#요리')">요리</a>
		</li>
		<li class="list_navi_li">
			<a href="javascript:move_search_cate('#일상')">일상</a>
		</li>
		<li class="list_navi_li">
			<a href="javascript:move_search_cate('#캠핑')">캠핑</a>
		</li>
		<li class="list_navi_li">
			<a href="javascript:move_search_cate('#리뷰')">리뷰</a>
		</li>
		<li class="list_navi_li">
			<a href="${ctxPath }/market/list">중고거래</a>
		</li>
	</ul>
	<input type="hidden" value="" id="board_keyword">
	<div class="list_main">
		
	</div>
</div>