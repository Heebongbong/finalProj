<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<div id="accuse_wrap">
	<div>신고 리스트 관리</div>
	<table>
		<tr>
			<td>글번호</td>
			<td>신고자번호</td>
			<td>게시글내용</td>
			<td>신고사유</td>
			<td>해당 게시글 삭제</td>
		</tr>
		
		<c:forEach items="${accuseList }" var="dto">
			<tr>
				<td>${dto.cm_no }</td>
				<td>${dto.user_no }</td>
				<td class="content">${dto.content }</td>
				<td>${dto.reason }</td>
				<td><a href="${ctxPath}/board/admin/delete?no=${dto.cm_no }">게시글 삭제</a></td>
			</tr>
		</c:forEach>
		
	</table>
	
		
</div>