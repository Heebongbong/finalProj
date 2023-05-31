<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="<%=request.getContextPath() %>" var="ctxPath"/>
<c:set value="${LoginUser }" var="loginUser"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="${ctxPath }/resources/images/logo/logo.png" rel="shortcut icon" type="image/x-icon">
<title><tiles:getAsString name="title"/></title>
<script type="text/javascript">
	const ctxPath = '${ctxPath }';
	const loginUser = '${loginUser }';
	const loginUser_no = '${loginUser.user_no }';
	const loginUser_profile = '${loginUser.profile }';
</script>
<script src="https://kit.fontawesome.com/e6bfca85af.js" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
<link rel="stylesheet" href="${ctxPath }<tiles:getAsString name="base_css" />">
<link rel="stylesheet" href="${ctxPath }<tiles:getAsString name="inc_css" />">
<link rel="stylesheet" href="${ctxPath }<tiles:getAsString name="css" />">
<script type="text/javascript" src="${ctxPath }<tiles:getAsString name="base_js" />"></script>
<script type="text/javascript" src="${ctxPath }<tiles:getAsString name="inc_js" />"></script>
<script type="text/javascript" src="${ctxPath }<tiles:getAsString name="js" />"></script>
</head>
<body>
	<div id="container">
		<tiles:insertAttribute name="header"/>
		<div id="content">
			<div id="side">
				<tiles:insertAttribute name="side"/>
			</div>
			<tiles:insertAttribute name="body" />
		</div>
	</div>
</body>
</html>