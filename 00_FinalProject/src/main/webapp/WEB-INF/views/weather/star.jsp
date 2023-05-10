<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Insert title here</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
	<style type="text/css">
		#container{
			text-align: center;
			width: 800px;
			margin: 100px auto;
		}
		#content{
			margin: 50px auto;
		}
		#star{
			width: 550px;
			height: 600px;
		}
	</style>
	<link href="${ctxPath }/resources/css/inc/layout.css" rel="stylesheet" />
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/inc/header.jsp" />
		<div id="content">
			<iframe id="star" src="https://stellarium-web.org/"></iframe>
		</div>
	</div>
</body>
</html>