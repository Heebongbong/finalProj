<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
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
	</style>
		<div id="content">
			<div id="weather_wrap">
				${str }
			</div>
		</div>
