<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<div id="camping_wrap">
	<h2>캠핑장 리스트 관리</h2>
    
    <button onclick="updateCampingList()">DB업데이트</button>
    
</div>