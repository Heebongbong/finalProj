<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="<%=request.getContextPath() %>" var="ctxPath"/>
<c:set value="${sessionScope.LoginUser }" var="loginUser"/>
<div id="header">
	<ul id="login_navi">
		<li><a href="${ctxPath }/indexNavi"><img alt="" src="${ctxPath }/resources/images/logo.png"></a></li>
		<li>
		<c:if test="${empty loginUser }">
			<a href="${ctxPath }/loginNavi">로그인</a>
		</c:if>
		<c:if test="${!empty loginUser }">
			<a href="${ctxPath }/login/logout">로그아웃</a>
		</c:if>
			&nbsp;&nbsp;<a href="javascript:open_user_menu()"><i class="fa fa-bars" aria-hidden="true"></i></a>
		</li>
	</ul>
	
	<div id="user_menu_wrap">
		<c:if test="${empty loginUser }"><!-- 비로그인시 -->
		<div id="user_menu_head">
			로그인 헤더1111<a href="javascript:close_user_menu()">X</a>
		</div>
		<div id="user_menu_body">
			<div id="user_menu_body_inp">
				<input name="email" placeholder="Email을 입력하세요">
				<input name="pwd" placeholder="비밀번호를 입력하세요." type="password">
			</div>
			<div id="user_menu_body_btn">
				<input type="submit" value="로그인">
				<input type="button" value="회원가입">
				<div>
					<a href="javascript:">이메일/비밀번호 찾기</a>
				</div>
			</div>
		</div>
		</c:if>
		<c:if test="${!empty loginUser }">
		<div id="user_menu_head"> <!-- 로그인시 -->
			<p>${loginUser.getNickname() }</p><a href="javascript:close_user_menu()">X</a>
		</div>
		<div id="user_menu_body">
			<p>로그인 되어있습니다.</p>
		</div>
		</c:if>
		<div id="user_menu_foot">
			회사 정보 카피라이트<br>약관 동의 뭐시기 뭐시기<br>
		</div>
	</div>
</div>