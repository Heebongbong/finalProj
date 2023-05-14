<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="<%=request.getContextPath() %>" var="ctxPath"/>
<c:set value="${sessionScope.LoginUser }" var="loginUser"/>
<div id="index_wrap">
	<c:if test="${!empty loginUser }">
		<p>Email : ${loginUser.getUser_email() }</p>
		<p>�г��� : ${loginUser.getUser_nickname() }</p>
		<p>����ó : ${loginUser.getUser_phone() }</p>
		<p><img alt="" src="${loginUser.getUser_profile() }"></p>
	</c:if>
	<c:if test="${empty loginUser }">
		<p>�α����� �ʿ��մϴ�.</p>
	</c:if>
</div>