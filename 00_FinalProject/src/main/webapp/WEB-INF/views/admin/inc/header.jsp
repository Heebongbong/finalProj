<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="<%=request.getContextPath() %>" var="ctxPath"/>
<c:set value="${sessionScope.LoginUser }" var="loginUser"/>
<div id="header">
	<a href="${ctxPath }/indexNavi"><img alt="" src="${ctxPath }/resources/images/logo/logo.png" width="50px" height="50px"></a>
	<a href="${ctxPath }/admin">관리자페이지</a>
</div>