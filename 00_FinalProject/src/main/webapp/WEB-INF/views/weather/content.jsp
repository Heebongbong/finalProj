<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
<div id="content_wrap">
	<c:if test="${!empty list }">
	<c:forEach items="${list }" var="dto">
		<p>${dto }</p>
	</c:forEach>
	</c:if>
</div>