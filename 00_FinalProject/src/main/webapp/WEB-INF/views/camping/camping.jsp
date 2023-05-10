<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
<div id="content">
	<input type="text" placeholder="페이지 수" id="nums">
	<input type="button" value="검색" onclick="locSearch()">
</div>
<script type="text/javascript">
	function locSearch() {
		let nums = $('#nums').val();
		location.href='${ctxPath}/camping/content?num='+nums;
	}
</script>