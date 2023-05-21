<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="<%=request.getContextPath() %>" var="ctxPath"/>
<c:set value="${sessionScope.LoginUser }" var="loginUser"/>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.5/sockjs.min.js"></script>
<div id="footer">
	<ul class="move_navi">
		<li class="navi_camping"><a href="${ctxPath }/campingNavi"><img alt="" src="${ctxPath }/resources/images/logo/tent.png"></a></li>
		<li><a href="${ctxPath }/weatherNavi">날씨</a></li>
		<li><a href="${ctxPath }/driveNavi">드라이브</a></li>
		<li><a href="${ctxPath }/board/list">커뮤니티</a></li>
		<li><a href="${ctxPath }/marketNavi">중고거래</a></li>
	</ul>
</div>

<div class="chat_open" onclick="open_chat()"><i class="fa fa-commenting" aria-hidden="true"></i></div>
		
<div class="chat_wrap">
	<div class="chat_list">
		<p><a href="javascript:chat_start(6)">채팅하기</a></p>
		<p>nickName</p>
		<p>nickName</p>
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