<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<div id="board_wrap">
	<div>
		<input type="button" value="게시글" onclick="location.href='${ctxPath }/board/list'">
	</div>
	
	<div>
		<input type="button" value="글쓰기" onclick="location.href='${ctxPath }/board/write'">
	</div>
</div>