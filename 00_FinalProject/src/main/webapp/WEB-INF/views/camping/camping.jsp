<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<div id="camping_wrap">
	<input type="text" placeholder="페이지 수" id="nums">
	<input type="button" value="검색" onclick="locSearch()">
</div>