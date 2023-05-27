<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<script>

let imgArray = [];
let imgIndex = 0;

<c:if test="${list.size() > 20 }">
	<c:forEach items="${list }" var="dto" begin="${list.size()-20 }" end="${list.size() }">
		imgArray.push('${dto }');
	</c:forEach>
</c:if>
<c:if test="${list.size() < 20 }">
	<c:forEach items="${list }" var="dto" begin="1" end="${list.size() }">
		imgArray.push('${dto }');
	</c:forEach>
</c:if>

let intervalId;
let picture1Img = $('#gifEffectImg');

</script>
<div id="star_wrap" style="width: 750px;">
	<div>
		<c:if test="${list.size() > 20 }">
			<img alt="" src="${list.get(list.size()-20) }" style="width: 350px; height: 350px;" id="gifEffectImg">
		</c:if>
		<c:if test="${list.size() < 20 }">
			<img alt="" src="${list.get(0) }" style="width: 350px; height: 350px;" id="gifEffectImg">
		</c:if>
	</div>
</div>


