<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<div id="camping_side">
	<ul class='camping_side_ul'>
		<li>
			<a href="${ctxPath }/admin/admin">채팅</a>
		</li>
		<li>
			<a href="${ctxPath }/admin/camping">캠핑</a>
		</li>
		<li>
			<a href="${ctxPath }/user/admin">유저</a>
		</li>
		<li>
			<a href="${ctxPath }/board/accuse">신고</a>
		</li>
	</ul>
	
	
</div>