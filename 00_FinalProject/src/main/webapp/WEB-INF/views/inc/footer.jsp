<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="<%=request.getContextPath() %>" var="ctxPath"/>
<div id="footer">
	<ul class="move_navi">
		<li><a class="navi_footer_a" href="${ctxPath }/weather/now"><img alt="" src="${ctxPath }/resources/images/icon/star_icon.png"><span>날씨</span></a></li>
		<li><a class="navi_footer_a" href="${ctxPath }/driveNavi"><img alt="" src="${ctxPath }/resources/images/icon/drive_icon.png"><span>드라이브</span></a></li>
		<li><a class="navi_footer_a" href="${ctxPath }/camping/camping"><img alt="" src="${ctxPath }/resources/images/logo/logo.png"><span>캠핑장</span></a></li>
		<li><a class="navi_footer_a" href="${ctxPath }/board/list"><img alt="" src="${ctxPath }/resources/images/icon/board_icon.png"><span>커뮤니티</span></a></li>
		<li><a class="navi_footer_a" href="${ctxPath }/market/list"><img alt="" src="${ctxPath }/resources/images/icon/market_icon.png"><span>중고거래</span></a></li>
	</ul>
</div>
<div class="top_write_navi">
	<div class="write_plus_navi">
		<img class="review_write_icon" onclick="move_write_page(${loginUser.isAuthen() })" alt="" src="${ctxPath }/resources/images/icon/review_write.png">
		<img class="market_write_icon" onclick="move_market_write_page(${loginUser.isAuthen() })" alt="" src="${ctxPath }/resources/images/icon/market_write.png">
		<img class="comm_write_icon" alt="" src="${ctxPath }/resources/images/icon/comm_write.png">
	</div>
	<img class="top_move_icon" alt="" src="${ctxPath }/resources/images/icon/top_icon(2).png" onclick="top_move_func()">
	<img class="plus_write_icon" alt="" src="${ctxPath }/resources/images/icon/plus_icon.png" onclick="open_footer_write()">
</div>