<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<c:set value="${BoardList }" var="boardList"/>
<div id="list_wrap">
	<c:forEach items="${boardList }" var="board">
		<div>
			${board }
		</div>
	</c:forEach>
</div>