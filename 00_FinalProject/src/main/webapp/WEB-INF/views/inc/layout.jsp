<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="<%=request.getContextPath() %>" var="ctxPath"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title><tiles:getAsString name="title"/></title>
<link rel="stylesheet" href="${ctxPath }<tiles:getAsString name="base_css" />">
</head>
<body>
	<div id="container">
		<tiles:insertAttribute name="header"/>
		<tiles:insertAttribute name="body"/>
		<tiles:insertAttribute name="footer"/>
	</div>
</body>
</html>