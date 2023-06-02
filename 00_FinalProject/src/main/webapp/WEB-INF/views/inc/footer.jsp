<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="<%=request.getContextPath() %>" var="ctxPath"/>
<c:set value="${sessionScope.LoginUser }" var="loginUser"/>
<c:set value="${ChatRoomList }" var="chatRoomList"/>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.5/sockjs.min.js"></script>
<script type="text/javascript">
	let loginUser_authen = '${loginUser.isAuthen() }';
</script>
<div id="footer">
	<ul class="move_navi">
		<li class="navi_camping"><a href="${ctxPath }/camping/camping"><img alt="" src="${ctxPath }/resources/images/logo/tent.png"></a></li>
		<li><a href="${ctxPath }/weather/now">날씨</a></li>
		<li><a href="${ctxPath }/driveNavi">드라이브</a></li>
		<li><a href="${ctxPath }/board/list">커뮤니티</a></li>
		<li><a href="${ctxPath }/market/list">중고거래</a></li>
	</ul>
</div>
<div class="chat_open" onclick="open_chat($())"><i class="fa fa-commenting" aria-hidden="true"></i></div>	
<div class="chat_wrap">
	<div class="chat_list">
		<p class="chat_list_p"><a href="javascript:chat_admin()">Admin</a></p>
		<c:forEach items="${chatRoomList }" var="room">
		<c:if test="${room.user_no1 == loginUser.user_no && (room.user_no1 != 1 && room.user_no2 != 1) }">
			<p class="chat_list_p" onmouseover="open_room_out(this)">
				<a href="javascript:chat_start(${room.user_no2 })"><img class="chat_list_img" alt="" src="${room.profile }">${room.nickname }</a>
				<button onclick="chat_room_out(${room.chat_room_no})" class="chat_room_out"><i class="fa fa-ellipsis-v" aria-hidden="true"></i></button>
			</p>
		</c:if>
		<c:if test="${room.user_no2 == loginUser.user_no && (room.user_no1 != 1 && room.user_no2 != 1) }">
			<p class="chat_list_p" onmouseover="open_room_out(this)">
				<a href="javascript:chat_start(${room.user_no1 })"><img class="chat_list_img" alt="" src="${room.profile }">${room.nickname }</a>
				<button onclick="chat_room_out(${room.chat_room_no}, this)" class="chat_room_out"><i class="fa fa-ellipsis-v" aria-hidden="true"></i></button>
			</p>
		</c:if>
		</c:forEach>
	</div>
	<div class="chat_main">
		<div class="chat_title">
			<h2>채팅 창</h2>
			<span class="chat_close" onclick="close_chat()">x</span>
		</div>
		<div class="chat_cont">
		
		</div>
		
		<div class="chat_btn">
			<input type="hidden" id="chat_receipt" value="">
			<input type="text" class="chat_msg" placeholder="Message">
			<input type="button" class="chat_send" value="Send" onclick="">
		</div>
	</div>
</div>