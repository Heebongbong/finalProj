<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>

<div id="star_wrap" style="width: 750px;">
	<c:forEach items="${list }" var="dto">
		<img alt="" src="${dto }" style="width: 350px; height: 350px;">
	</c:forEach>
</div>