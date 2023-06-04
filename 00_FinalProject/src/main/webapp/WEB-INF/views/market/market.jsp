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
			<a href="javascript:open_navi(0)">지역별</a>
			<a href="javascript:open_navi(1)">상품별</a>
		</div>
		<div class="market_navi_cont">
			<div class="cate_cont">
				<ul class="loc_ul">
					<li class="loc_li">
						<a href="javascript:open_navi_loc(0)">서울  / 인천</a>
						<ul class="loc_cont">
							<li>
								<a href="javascript:move_search_cate('서울')">서울</a>
							</li>
							<li>
								<a href="javascript:move_search_cate('인천')">인천</a>
							</li>
						</ul>
					</li>
					<li class="loc_li">
						<a href="javascript:open_navi_loc(1)">경기도 / 강원도</a>
						<ul class="loc_cont">
							<li>
								<a href="javascript:move_search_cate('경기')">경기도</a>
							</li>
							<li>
								<a href="javascript:move_search_cate('강원')">강원도</a>
							</li>
						</ul>
					</li>
					<li class="loc_li">
						<a href="javascript:open_navi_loc(2)">충청도 / 전라도</a>
						<ul class="loc_cont">
							<li>
								<a href="javascript:move_search_cate('충청')">충청도</a>
							</li>
							<li>
								<a href="javascript:move_search_cate('전라')">전라도</a>
							</li>
						</ul>
					</li>
					<li class="loc_li">
						<a href="javascript:open_navi_loc(3)">경상도 / 제주도</a>
						<ul class="loc_cont">
							<li>
								<a href="javascript:move_search_cate('경상')">경상도</a>
							</li>
							<li>
								<a href="javascript:move_search_cate('제주')">제주</a>
							</li>
						</ul>
					</li>	
				</ul>
			</div>
			<div class="cate_cont">
				<ul class="obj_ul">
					<li class="obj_li">
						<a href="${ctxPath }/market/list">전체</a>
					</li>
					<li class="obj_li">
						<a href="javascript:move_search_cate('텐트%23타프')">텐트/타프</a>
					</li>
					<li class="obj_li">
						<a href="javascript:move_search_cate('테이블%23체어')">테이블/체어</a>
					</li>
					<li class="obj_li">
						<a href="javascript:move_search_cate('취사%23수납')">취사/수납</a>
					</li>
					<li class="obj_li">
						<a href="javascript:move_search_cate('랜턴%23조명')">랜턴/조명</a>
					</li>
					<li class="obj_li">
						<a href="javascript:move_search_cate('소품%23기타')">소품/기타</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<div class="market_declaration_check">
		<a href="javascript:" onclick="fraud_check_window()"><i class="fa fa-search-minus" aria-hidden="true"></i></a>
	</div>
	<form class="market_search" action="" method="get">
		<input type="text" value="${Keyword }" id="market_keyword" placeholder="검색어를 입력하세요." name="keyword"><input type="submit" value="검색">
	</form>
	<input type="hidden" value="${Category }" id="market_category">
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
	<div class="market_main">
		
	</div>
</div>