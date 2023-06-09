<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="<%=request.getContextPath() %>" var="ctxPath"/>
<c:set value="${sessionScope.LoginUser }" var="loginUser"/>
<c:set value="${AlarmList }" var="alarmList"/>
<c:set value="${ChatRoomList }" var="chatRoomList"/>
<%
	StringBuffer ctxUrl = request.getRequestURL();
	String reUrl = ctxUrl.substring(0, ctxUrl.indexOf("finproj"));
%>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.5/sockjs.min.js"></script>
<script type="text/javascript">
	let loginUser_authen = '${loginUser.isAuthen() }';
	let loginUser_nickname = '${loginUser.nickname }';
</script>
<div id="header">
	<ul class="login_navi">
		<li class="login_navi_logo_li"><a href="${ctxPath }/indexNavi"><img alt="" src="${ctxPath }/resources/images/logo/logo.png"></a></li>
		<li class="login_navi_search_li">
		<c:if test="${empty M_check }">
			<form action="${ctxPath }/board/list" method="post">
				<input type="text" id="search_keyword" placeholder="검색어를 입력하세요" name="keyword" value="<c:if test="${!empty Keyword }">${Keyword }</c:if>">
				<button type="submit"><i class="fa fa-search" aria-hidden="true"></i></button>
			</form>		
		</c:if>
		<c:if test="${!empty M_check }">
			<form action="${ctxPath }/market/list" method="post">
				<input type="text" value="${M_Keyword }" id="market_keyword" placeholder="중고거래 검색" name="keyword">
				<button type="submit"><i class="fa fa-search" aria-hidden="true"></i></button>
			</form>
		</c:if>
		</li>
		<c:if test="${empty loginUser }"><!-- 비로그인시 -->
			<li class="user_menu_profile_non">
				<a href="javascript:open_user_menu()"><i class="fa fa-bars" aria-hidden="true"></i></a>
			</li>
		</c:if>
		<c:if test="${!empty loginUser }"><!-- 로그인시 -->
			<li class="user_menu_profile">
				<c:if test="${alarmList.get('new_check') == 0 }">
					<a href="javascript:alarm_modal()" onclick="" class="alarm_icon"><img class="profile_img_alarm" src="${ctxPath }/resources/images/icon/alarm.png"></a>
				</c:if>
				<c:if test="${alarmList.get('new_check') != 0 }">
					<a href="javascript:alarm_modal()" onclick="" class="alarm_icon"><img class="profile_img_alarm" src="${ctxPath }/resources/images/icon/alarm_o.png"></a>
				</c:if>
				<c:if test="${alarmList.get('chat_on').getTotalCount() == 0 }">
					<a class="chat_open" href="javascript:open_chat(0)"><img src="${ctxPath }/resources/images/icon/chat_icon.png" alt=""></a>
				</c:if>
				<c:if test="${alarmList.get('chat_on').getTotalCount() != 0 }">
					<a class="chat_open" href="javascript:open_chat(1)"><img src="${ctxPath }/resources/images/icon/chat_o.png" alt=""></a>
				</c:if>
				<a href="javascript:open_user_menu()"><img class="user_menu_prof_img" src="${loginUser.profile }"></a>
			</li>
			<div class="alarm_modal_overlay">
				<div class="alarm_modal_window">
					<c:if test="${alarmList.get('board_like').getTotalCount() != 0 }">
						<p><a href="javascript:alarm_move_href(1)"><img alt="" src="${ctxPath }/resources/images/icon/alarm_b_like_icon.png">
						<span>당신의 게시물을 ${alarmList.get('board_like').getTotalCount() }명이 더 좋아해요.<br>
							(새로운 알람 : ${alarmList.get('board_like').getCheckCount() })</span>
						</a></p>
					</c:if>
					<c:if test="${alarmList.get('ment_like').getTotalCount() != 0 }">
						<p><a href="javascript:alarm_move_href(2)"><img alt="" src="${ctxPath }/resources/images/icon/alarm_m_like_icon.png">
							<span>당신의 댓글을 ${alarmList.get('ment_like').getTotalCount() }명이 더 좋아해요.<br>
							(새로운 알람 : ${alarmList.get('ment_like').getCheckCount() })</span>
						</a></p>
					</c:if>
					<c:if test="${alarmList.get('ment_ins').getTotalCount() != 0 }">
						<p><a href="javascript:alarm_move_href(3)"><img alt="" src="${ctxPath }/resources/images/icon/alarm_ment_icon.png">
							<span>게시물에 새로운 댓글이 ${alarmList.get('ment_ins').getTotalCount() }개 달렸어요!<br>
							(새로운 알람 : ${alarmList.get('ment_ins').getCheckCount() })</span>
						</a></p>
					</c:if>
					<c:if test="${alarmList.get('new_check') == 0 }">
						<p><a href="javascript:"><img alt="" src="${ctxPath }/resources/images/icon/top_icon(2).png">새로운 알람이 없습니다.</a></p>
					</c:if>
				</div>
			</div>
		</c:if>
	</ul>
	
	<!-- chat manage  -->
	<div class="chat_wrap">
		<div class="chat_title">
			<p class="chat_title_p chat_title_p_user"><a href="javascript:"><img class="chat_title_img" alt="" src="${loginUser.profile }">${loginUser.nickname }</a><button onclick="close_chat()">닫기</button></p>
			<p class="chat_title_p chat_title_p_send"><a href="javascript:"><img class="chat_title_img chat_title_img_send" alt="" src="${ctxPath }/resources/images/profile/default/default_profile.png"><span class="chat_title_nick_send"></span></a></p>			
		</div>
		<div class="chat_list_main">
			<div class="chat_list">
				<%--admin chat --%>
				<c:if test="${!empty Admin_chat_room }">
					<p class="chat_list_p"><a href="javascript:chat_admin(${Admin_chat_room.chat_room_no })"><img class="chat_list_img" alt="" src="${ctxPath }/resources/images/profile/default/default_profile.png">Admin</a></p>
				</c:if>
				
				<c:forEach items="${chatRoomList }" var="room">
				
				<%-- users chat --%>
				<c:if test="${room.user_no1 == loginUser.user_no && (room.user_no1 != 1 && room.user_no2 != 1) }">
					<p class="chat_list_p" onmouseover="open_room_out(this)">
						<a href="javascript:chat_start(${room.user_no2 }, ${room.chat_room_no })"><img class="chat_list_img" alt="" src="${room.profile }">${room.nickname }</a>
						<button onclick="chat_room_out(${room.chat_room_no }, this, '${room.nickname }', ${room.user_no2 })" class="chat_room_out"><i class="fa fa-ellipsis-v" aria-hidden="true"></i></button>
					</p>
				</c:if>
				<c:if test="${room.user_no2 == loginUser.user_no && (room.user_no1 != 1 && room.user_no2 != 1) }">
					<p class="chat_list_p" onmouseover="open_room_out(this)">
						<a href="javascript:chat_start(${room.user_no1 }, ${room.chat_room_no })"><img class="chat_list_img" alt="" src="${room.profile }">${room.nickname }</a>
						<button onclick="chat_room_out(${room.chat_room_no }, this, '${room.nickname }', ${room.user_no1 })" class="chat_room_out"><i class="fa fa-ellipsis-v" aria-hidden="true"></i></button>
					</p>
				</c:if>
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
	
	<div class="user_menu_wrap">
		<c:if test="${empty loginUser }"><!-- 비로그인시 -->
		<div class="user_menu_body">
			<form action="${ctxPath }/login/site" method="post" onsubmit="return check_login_inp()">
				<div class="user_menu_body_inp">
					<div class="user_menu_body_inp_cont">
						<label for="user_menu_email_inp">이메일</label>
						<input id="user_menu_email_inp" name="email" placeholder="Email을 입력하세요">
					</div>
					<div class="user_menu_body_inp_cont">
						<label for="user_menu_pwd_inp">비밀번호</label>
						<input id="user_menu_pwd_inp" name="pwd" placeholder="비밀번호를 입력하세요." type="password">
					</div>
					<div class="user_join_search">
						<a href='${ctxPath}/user/join'>회원가입</a>&nbsp;|&nbsp;<a href="${ctxPath }/user/forget">비밀번호 찾기</a>
					</div>
				</div>
				<input class="user_login_btn" type="submit" value="로그인">
			</form>
			<div class="user_menu_body_sns">
				
				<script type="text/javascript"> const reUrl = '<%=reUrl %>';</script>
				<a class="naver_login_btn" href="https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=2fzdhIRlmXgPi9uo_5Xi&redirect_uri=http%3A%2F%2Flocalhost%3A8787%2Ffinproj%2Flogin%2Fnaver&state=a83abeaf-9d9d-4bdb-a4f1-d3af1fe30cf1">
					<img alt="" src="${ctxPath }/resources/images/logo/naver_logo.jpg">
				</a>
				<a class="kakao_login_btn" href="javascript:loginWithKakao()">
					<img alt="" src="${ctxPath }/resources/images/logo/kakao_logo.png">
				</a>
				<script src="https://t1.kakaocdn.net/kakao_js_sdk/2.1.0/kakao.min.js" integrity="sha384-dpu02ieKC6NUeKFoGMOKz6102CLEWi9+5RQjWSV0ikYSFFd8M3Wp2reIcquJOemx" crossorigin="anonymous"></script>
				<script>Kakao.init('78f087e4814e7f60f4ee016cae934876');</script>
				
				<a class="google_login_btn" href="javascript:loginWithGoogle()">
					<img alt="" src="${ctxPath }/resources/images/logo/google_logo.png">
				</a>
			</div>
		</div>
		</c:if>
		
		<!-- 로그인 시 -->
		<c:if test="${!empty loginUser }">
		<div class="user_menu_body">
			<p class="user_menu_logon_cont">
				<img alt="" src="${loginUser.getProfile() }">
			</p>
			<p class="user_menu_logon_cont">
				<span style="font-weight: bold;">닉네임</span>
				<span>${loginUser.getNickname() }</span>
			</p>
			<div class="user_menu_logon_cont">
				<a href="${ctxPath }/login/logout">로그아웃</a>
				<a href="${ctxPath }/user/mypage">마이페이지</a>
			</div>
			<div class="user_menu_logon_cont">
				<a href="${ctxPath }/user/userboard?user_no=${loginUser.getUser_no() }">내 게시물</a>
				<a href="${ctxPath }/user/likeboard">내가 좋아요한 게시물</a>
			</div>
		</div>
		</c:if>
		
		<div class="user_menu_foot">
			Copyright © 2023 CAMPION<br>ORGANIZATION ALL RIGHTS RESERVED.<br>
		</div>
	</div>
</div>