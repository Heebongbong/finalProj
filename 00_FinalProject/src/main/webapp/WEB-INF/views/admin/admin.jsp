<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<c:set var="chatRoomList" value="${ChatRoomList }"/>
<div id="admin_wrap">
	<h2>관리자채팅</h2>
	<!-- chat manage  -->
	<div>
		<input type="text"><input type="button" value="채팅 신청" onclick="chat_board()">
	</div>
	<div class="chat_wrap">
		<div class="chat_list">
			<c:forEach items="${chatRoomList }" var="room">
				<p class="chat_list_p">
					<a href="javascript:chat_start(${room.user_no1 })"><img class="chat_list_img" alt="" src="${room.profile }">${room.nickname }</a>
					<button onclick="chat_room_out(${room.chat_room_no}, this)" class="chat_room_out"><i class="fa fa-ellipsis-v" aria-hidden="true"></i></button>
				</p>
			</c:forEach>
		</div>
		<div class="chat_main">
			<div class="chat_cont">
			
			</div>
			
			<div class="chat_btn">
				<input type="hidden" id="chat_receipt" value="">
				<input type="text" onkeydown="" class="chat_msg" placeholder="Message" >
				<Button class="chat_send" onclick=""><img class="chat_list_img" alt="" src="${ctxPath }/resources/images/icon/m_send_icon.png"></Button>
			</div>
		</div>
	</div>
</div>