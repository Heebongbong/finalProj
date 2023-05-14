<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="<%=request.getContextPath() %>" var="ctxPath"/>
<c:set value="${sessionScope.LoginUser }" var="loginUser"/>
<div id="header">
	<ul id="login_navi">
		<c:if test="${empty loginUser }">
			<li><a href="${ctxPath }/loginNavi">�α���</a></li>
		</c:if>
		<c:if test="${!empty loginUser }">
			<li>${loginUser.getUser_nickname() }�� ȯ���մϴ�.</li>
			<li><a href="${ctxPath }/login/logout">�α׾ƿ�</a></li>
		</c:if>
	</ul>
	<ul id="move_navi">
		<li><a href="${ctxPath }/indexNavi">Ȩ</a></li>
		<li><a href="${ctxPath }/campingNavi">ķ����</a></li>
		<li><a href="${ctxPath }/weatherNavi">����</a></li>
		<li><a href="${ctxPath }/driveNavi">����̺�</a></li>
		<li><a href="${ctxPath }/boardNavi">Ŀ�´�Ƽ</a></li>
		<li><a href="${ctxPath }/marketNavi">�߰�ŷ�</a></li>
	</ul>
</div>