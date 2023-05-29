<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="<%=request.getContextPath() %>" var="ctxPath"/>
<c:set value="${sessionScope.LoginUser }" var="loginUser"/>

<div id="security_wrap">
	<iframe src="https://cyberbureau.police.go.kr/prevention/nw/sub7.jsp?mid=020600"></iframe>
</div>