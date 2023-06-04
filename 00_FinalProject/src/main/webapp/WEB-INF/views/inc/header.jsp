<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="<%=request.getContextPath() %>" var="ctxPath"/>
<c:set value="${sessionScope.LoginUser }" var="loginUser"/>
<c:set value="${AlarmList }" var="alarmList"/>
<div id="header">
	<ul class="login_navi">
		<li><a href="${ctxPath }/indexNavi"><img alt="" src="${ctxPath }/resources/images/logo/logo.png"></a></li>
		<li>
			<form action="${ctxPath }/board/list" method="post">
				<input type="text" id="search_keyword" placeholder="검색창" name="keyword" value="<c:if test="${!empty Keyword }">${Keyword }</c:if>">
				<input type="submit" value="검색">
			</form>
		</li>
		<c:if test="${empty loginUser }"><!-- 비로그인시 -->
			<li class="user_menu_profile">
				<a href="javascript:open_user_menu()"><img src="${ctxPath }/resources/images/logo/logo.png"></a>
			</li>
		</c:if>
		<c:if test="${!empty loginUser }"><!-- 로그인시 -->
			<li class="user_menu_profile">
				<c:if test="${alarmList.get('new_check') == 0 }">
					<a href="javascript:alarm_modal()" onclick="" class="alarm_icon"><i class="fa fa-bell-o" aria-hidden="true"></i></a>
				</c:if>
				<c:if test="${alarmList.get('new_check') != 0 }">
					<a href="javascript:alarm_modal()" onclick="" class="alarm_icon"><i class="fa fa-bell" aria-hidden="true"></i></a>
				</c:if>
				<a href="javascript:open_user_menu()"><img src="${loginUser.profile }"></a>
			</li>
			<div class="alarm_modal_overlay">
				<div class="alarm_modal_window">
					<c:if test="${alarmList.get('board_like').getTotalCount() != 0 }">
						<p><a href="javascript:alarm_move_href(1)"><img alt="" src="${ctxPath }/resources/images/icon/alarm_b_like_icon.png">당신의 게시물을 ${alarmList.get('board_like').getCheckCount() }명이 더 좋아해요.</a></p>
					</c:if>
					<c:if test="${alarmList.get('ment_like').getTotalCount() != 0 }">
						<p><a href="javascript:alarm_move_href(2)"><img alt="" src="${ctxPath }/resources/images/icon/alarm_m_like_icon.png">당신의 댓글을 ${alarmList.get('ment_like').getCheckCount() }명이 더 좋아해요.</a></p>
					</c:if>
					<c:if test="${alarmList.get('ment_ins').getTotalCount() != 0 }">
						<p><a href="javascript:alarm_move_href(3)"><img alt="" src="${ctxPath }/resources/images/icon/alarm_ment_icon.png">게시물에 새로운 댓글이 ${alarmList.get('ment_ins').getCheckCount() }개 달렸어요!</a></p>
					</c:if>
<<<<<<< Upstream, based on branch 'NJH' of https://github.com/Heebongbong/finalProj.git
					<c:if test="${alarmList.get('chat_on').getTotalCount() != 0 }">
						<p><a href="javascript:alarm_move_href(4)">누군가 회원님께 채팅을 신청했습니다.<br>(새로운 알림 : ${alarmList.get('chat_on').getCheckCount() })</a></p>
					</c:if>
=======
					<%-- <c:if test="${alarmList.get('chat_on').getTotalCount() != 0 }">
						<p><a href="javascript:alarm_move_href(4)"> ${alarmList.get('chat_on').getCheckCount() }</a></p>
					</c:if> --%>
>>>>>>> f4c1c2a LHY index -v2.0
				</div>
			</div>
		</c:if>
	</ul>
	
	<div class="user_menu_wrap">
		<c:if test="${empty loginUser }"><!-- 비로그인시 -->
		<div class="user_menu_head">
			로그인 헤더<a href="javascript:close_user_menu()">X</a>
		</div>
		<div class="user_menu_body">
			<form action="${ctxPath }/login/site" method="post">
				<div class="user_menu_body_inp">
					<input name="email" placeholder="Email을 입력하세요">
					<input name="pwd" placeholder="비밀번호를 입력하세요." type="password">
				</div>
				<div class="user_menu_body_btn">
					<input type="submit" value="로그인">
					<input type="button" onclick="location.href='${ctxPath}/user/join'" value="회원가입">
					<div>
						<a href="${ctxPath }/user/forget">비밀번호 찾기</a>
					</div>
				</div>
			</form>
			<div class="user_menu_body_sns">
				<%
					StringBuffer ctxUrl = request.getRequestURL();
					String reUrl = ctxUrl.substring(0, ctxUrl.indexOf("finproj"));
				%>
				<script type="text/javascript"> const reUrl = '<%=reUrl %>';</script>
				<a class="naver_login_btn" href="https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=2fzdhIRlmXgPi9uo_5Xi&redirect_uri=http%3A%2F%2Flocalhost%3A8787%2Ffinproj%2Flogin%2Fnaver&state=a83abeaf-9d9d-4bdb-a4f1-d3af1fe30cf1">
					<img style="width: 50px; height: 50px;" alt="" src="${ctxPath }/resources/images/logo/naver_logo.jpg">
				</a>
				<a class="kakao_login_btn" href="javascript:loginWithKakao()">
					<img style="width: 50px; height: 50px;" alt="" src="${ctxPath }/resources/images/logo/kakao_logo.png">
				</a>
				<script src="https://t1.kakaocdn.net/kakao_js_sdk/2.1.0/kakao.min.js" integrity="sha384-dpu02ieKC6NUeKFoGMOKz6102CLEWi9+5RQjWSV0ikYSFFd8M3Wp2reIcquJOemx" crossorigin="anonymous"></script>
				<script>Kakao.init('78f087e4814e7f60f4ee016cae934876');</script>
				
				<a class="google_login_btn" href="javascript:loginWithGoogle()">
					<img style="width: 50px; height: 50px;" alt="" src="${ctxPath }/resources/images/logo/google_logo.png">
				</a>
				
				
			</div>
		</div>
		</c:if>
		
		<c:if test="${!empty loginUser }">
		<div class="user_menu_head"> <!-- 로그인시 -->
			<div>
				<a href="javascript:close_user_menu()">X</a>
				<p><img alt="" src="${loginUser.getProfile() }"></p>
				<p>${loginUser.getNickname() }</p>
				<input type="button" value="로그아웃" onclick="location.href='${ctxPath }/login/logout'">
				<button onclick="location.href='${ctxPath }/user/mypage'">마이페이지</button>
				<button onclick="delete_move('${loginUser.getType()}')">회원탈퇴</button>
			</div>
			<div>
				<a href="${ctxPath }/user/userboard?user_no=${loginUser.getUser_no() }">내 게시물</a>
				<a href="${ctxPath }/user/likeboard">내가 좋아요한 게시물</a>
			</div>
			
		</div>
		<div class="user_menu_body">
			<div></div>
		</div>
		</c:if>
		
		<div class="user_menu_foot">
			회사 정보 카피라이트<br>약관 동의 뭐시기 뭐시기<br>
		</div>
	</div>
</div>