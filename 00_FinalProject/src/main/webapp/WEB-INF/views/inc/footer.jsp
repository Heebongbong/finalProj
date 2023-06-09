<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="<%=request.getContextPath() %>" var="ctxPath"/>
<c:set value="${sessionScope.LoginUser }" var="loginUser"/>
<div class="top_write_navi">
	<div class="write_plus_navi">
		<img class="review_write_icon" onclick="if(confirm('캠핑 리뷰는 캠핑장 리스트에서 접근이 가능합니다.\n캠핑장 리스트로 이동하시겠습니까.')){location.href='${ctxPath }/camping/camping'}else{return ;}" alt="" src="${ctxPath }/resources/images/icon/review_write.png">
		<img class="market_write_icon" onclick="move_market_write_page(${loginUser.isAuthen() })" alt="" src="${ctxPath }/resources/images/icon/market_write.png">
		<img class="comm_write_icon" onclick="move_write_page(${loginUser.isAuthen() })" alt="" src="${ctxPath }/resources/images/icon/comm_write.png">
	</div>
	<img class="top_move_icon" alt="" src="${ctxPath }/resources/images/icon/top_icon(2).png" onclick="top_move_func()">
	<c:if test="${empty Content }">	
		<img class="plus_write_icon plus_write_icon_pl" alt="" src="${ctxPath }/resources/images/icon/plus_icon.png" onclick="open_footer_write()">
	</c:if>
	<c:if test="${!empty Content }">
		<img class="plus_write_icon plus_write_icon_pen" alt="" src="${ctxPath }/resources/images/icon/pencil_icon.png" onclick="review_write_move(${Content.getContent_id() }, ${loginUser.isAuthen() })">
	</c:if>
</div>
<div id="footer">
	<ul class="move_navi">
		<li><a class="navi_footer_a" id="footer_weather" href="${ctxPath }/weather/now"><img alt="" src="${ctxPath }/resources/images/icon/weather_icon.png"><span>날씨</span></a></li>
		<li><a class="navi_footer_a" id="footer_drive" href="${ctxPath }/driveNavi"><img alt="" src="${ctxPath }/resources/images/icon/drive_icon.png"><span>드라이브</span></a></li>
		<li><a class="navi_footer_a" id="footer_camp" href="${ctxPath }/camping/camping"><img alt="" src="${ctxPath }/resources/images/icon/camp_icon.png"><span>캠핑장</span></a></li>
		<li><a class="navi_footer_a" id="footer_board" href="${ctxPath }/board/list"><img alt="" src="${ctxPath }/resources/images/icon/board_icon.png"><span>커뮤니티</span></a></li>
		<li><a class="navi_footer_a" id="footer_market" href="${ctxPath }/market/list"><img alt="" src="${ctxPath }/resources/images/icon/market_icon.png"><span>중고거래</span></a></li>
	</ul>
</div>