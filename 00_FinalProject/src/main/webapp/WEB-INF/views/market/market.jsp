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
								서울
							</li>
							<li>
								인천
							</li>
						</ul>
					</li>
					<li class="loc_li">
						<a href="javascript:open_navi_loc(1)">경기도 / 강원도</a>
						<ul class="loc_cont">
							<li>
								경기도
							</li>
							<li>
								강원도
							</li>
						</ul>
					</li>
					<li class="loc_li">
						<a href="javascript:open_navi_loc(2)">충청도 / 전라도</a>
						<ul class="loc_cont">
							<li>
								충청도
							</li>
							<li>
								전라도
							</li>
						</ul>
					</li>
					<li class="loc_li">
						<a href="javascript:open_navi_loc(3)">경상도 / 제주도</a>
						<ul class="loc_cont">
							<li>
								경상도
							</li>
							<li>
								제주
							</li>
						</ul>
					</li>	
				</ul>
			</div>
			<div class="cate_cont">
				<ul class="obj_ul">
					<li class="obj_li">
						요리
					</li>
					<li class="obj_li">
						캠핑
					</li>
					<li class="obj_li">
						대형
					</li>
					<li class="obj_li">
						기타
					</li>
				</ul>
			</div>
		</div>
	</div>
	<div class="market_main">
		${marketList }
	</div>
</div>