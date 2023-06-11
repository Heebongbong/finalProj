<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<div id="user_wrap">
	<table>
		<tr>
			<th>번호</th>
			<th>이메일</th>
			<th>가입처</th>
			<th>닉네임</th>
			<th>핸드폰</th>
			<th>프로필</th>
			<th>authen</th>
			<th>profile_type</th>
			<th>유저삭제</th>
		</tr>
		
		<c:forEach items="${userList }" var="dto">
			<tr>
				<td>${dto.user_no }</td>
				<td>${dto.email }</td>
				<td>${dto.type }</td>
				<td>${dto.nickname }</td>
				<td>${dto.phone }</td>
				<td><img src="${dto.profile }" width="50px" height="50px"></td>
				<td>${dto.authen }</td>
				<td>${dto.profile_type }</td>
				<td><a href="#" onclick="if(confirm('정말로 삭제하시겠습니까?')) {location.href='${ctxPath}/user/admin/delete?user_no=${dto.user_no }'}">삭제</a></td>
			</tr>
		</c:forEach>
		
	</table>
</div>