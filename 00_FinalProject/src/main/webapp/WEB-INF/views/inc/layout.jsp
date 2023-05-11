<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="<%=request.getContextPath() %>" var="ctxPath"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="${ctxPath }/resources/images/logo.png" rel="shortcut icon" type="image/x-icon">
<title><tiles:getAsString name="title"/></title>
<link rel="stylesheet" href="${ctxPath }<tiles:getAsString name="base_css" />">
</head>
<body>
	<div id="container">
		<tiles:insertAttribute name="header"/>
		<div id="content">
			<tiles:insertAttribute name="body"/>
		</div>
		<tiles:insertAttribute name="footer"/>
	</div>
</body>
</html>