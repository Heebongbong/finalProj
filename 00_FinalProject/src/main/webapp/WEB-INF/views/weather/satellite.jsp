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
		#weather_wrap{
			display: flex;
			justify-content: flex-start;
			flex-wrap: wrap;
		}
		#test{
			width: 400px;
			border: 1px solid black;
			box-sizing: border-box;
		}
		#satellite_wrap{
			display: flex;
			justify-content: space-between;
			flex-wrap: wrap;
		}
		#satellite_wrap img{
			width: 380px;
		}
	</style>
	<link href="${ctxPath }/resources/css/inc/layout.css" rel="stylesheet" />
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/inc/header.jsp" />
		<div id="content">
			<div id="satellite_wrap">
				<c:forEach items="${list }" var="dto">
					<img alt="" src="${dto }">
				</c:forEach>
			</div>
		</div>
	</div>
</body>
</html>