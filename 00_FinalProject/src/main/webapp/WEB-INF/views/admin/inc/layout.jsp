<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="<%=request.getContextPath() %>" var="ctxPath"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><tiles:getAsString name="title"/></title>
<script type="text/javascript">
	const ctxPath = '${ctxPath }';
</script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css" integrity="sha512-5A8nwdMOWrSz20fDsjczgUidUBR8liPYU+WymTZP1lmY9G6Oc7HlZv156XqnsgNUzTyMefFTcsFH/tnJE/+xBg==" crossorigin="anonymous" referrerpolicy="no-referrer" />

<!-- <script src="https://kit.fontawesome.com/e6bfca85af.js" crossorigin="anonymous"></script>
 -->

<link rel="stylesheet" href="${ctxPath }<tiles:getAsString name="inc_css" />">
<link rel="stylesheet" href="${ctxPath }<tiles:getAsString name="css" />">
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
			<div id="body">
				<tiles:insertAttribute name="body" />
			</div>
		</div>
	</div>
</body>
</html>