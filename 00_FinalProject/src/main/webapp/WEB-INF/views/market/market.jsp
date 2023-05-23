<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="<%=request.getContextPath() %>" var="ctxPath"/>
<c:set value="${sessionScope.LoginUser }" var="loginUser"/>
<c:set value="${MarketList }" var="marketList" />
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
								<a href="${ctxPath }/market/list?keyword=서울">서울</a>
							</li>
							<li>
								<a href="${ctxPath }/market/list?keyword=인천">인천</a>
							</li>
						</ul>
					</li>
					<li class="loc_li">
						<a href="javascript:open_navi_loc(1)">경기도 / 강원도</a>
						<ul class="loc_cont">
							<li>
								<a href="${ctxPath }/market/list?keyword=경기">경기도</a>
							</li>
							<li>
								<a href="${ctxPath }/market/list?keyword=강원">강원도</a>
							</li>
						</ul>
					</li>
					<li class="loc_li">
						<a href="javascript:open_navi_loc(2)">충청도 / 전라도</a>
						<ul class="loc_cont">
							<li>
								<a href="${ctxPath }/market/list?keyword=충청">충청도</a>
							</li>
							<li>
								<a href="${ctxPath }/market/list?keyword=전라">전라도</a>
							</li>
						</ul>
					</li>
					<li class="loc_li">
						<a href="javascript:open_navi_loc(3)">경상도 / 제주도</a>
						<ul class="loc_cont">
							<li>
								<a href="${ctxPath }/market/list?keyword=경상">경상도</a>
							</li>
							<li>
								<a href="${ctxPath }/market/list?keyword=제주">제주</a>
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
						<a href="${ctxPath }/market/list?keyword=#텐트#타프">텐트/타프</a>
					</li>
					<li class="obj_li">
						<a href="${ctxPath }/market/list?keyword=#테이블#체어">테이블/체어</a>
					</li>
					<li class="obj_li">
						<a href="${ctxPath }/market/list?keyword=#취사#수납">취사/수납</a>
					</li>
					<li class="obj_li">
						<a href="${ctxPath }/market/list?keyword=#랜턴#조명">랜턴/조명</a>
					</li>
					<li class="obj_li">
						<a href="${ctxPath }/market/list?keyword=#소품#기타">소품/기타</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<div class="market_write">
		<a href="${ctxPath }/market/write"><i class="fa fa-pencil" aria-hidden="true"></i></a>
	</div>
	<form class="market_search" action="" method="get">
		<input type="text" placeholder="검색어를 입력하세요." name="keyword"><input type="submit" value="검색">
	</form>
	<div class="market_main">
		<c:forEach items="${marketList }" var="dto">
			<div>
				${dto }
			</div>
		</c:forEach>
		
	</div>
</div>