<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<script>

let imgArray = [];
let imgIndex = 0;

<c:if test="${list.size() > 30 }">
	<c:forEach items="${list }" var="dto" begin="${list.size()-30 }" end="${list.size() }">
		imgArray.push('${dto }');
	</c:forEach>
</c:if>
<c:if test="${list.size() < 30 }">
	<c:forEach items="${list }" var="dto" begin="1" end="${list.size() }">
		imgArray.push('${dto }');
	</c:forEach>
</c:if>

let intervalId;
let picture1Img = $('#gifEffectImg');

</script>
<style type="text/css">
	#content{
		width: 100%;
	}
	#star_wrap img{
		width: 100%;
	}
</style>
<div id="star_wrap" style="width: 100%; height: 100%">
	<div style="text-align: center">
		<c:if test="${list.size() > 30 }">
			<img alt="" src="${list.get(list.size()-30) }" id="gifEffectImg">
		</c:if>
		<c:if test="${list.size() < 30 }">
			<img alt="" src="${list.get(0) }" id="gifEffectImg">
		</c:if>
	</div>
</div>


