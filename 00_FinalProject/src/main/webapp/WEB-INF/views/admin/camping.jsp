<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<div id="camping_wrap">
	<div>캠핑장 리스트 관리</div>
    
    <button onclick="insertCampingList()">DB추가</button>
    <button onclick="deleteCampingList()">DB삭제</button>
    
</div>