<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<div id="camping_wrap">
	<div id="camping_search_wrap">
		<c:forEach items="${CampingList }" var="dto">
			<div class="camping_list_img">
				<img src="${dto.getFirstImageUrl() }" alt="캠핑장 대표 이미지" class="camping_image" width="200px" height="200px">
			</div>
			<div class="camping_list_info">
				<input type="hidden" value="${dto.getContent_id() }" class="content_id">
				<p>이름:<a href="${ctxPath }/camping/details?content_id=${dto.getContent_id() }" class="camping_title">${dto.getFacltNm() }</a> </p>
				<p>소개: ${dto.getLineIntro() }</p>
				<p><input type="text" value="수정하기" ></p>
				<p><input type="text" value="수정하기" ></p>
			</div>
		</c:forEach>
	</div>
</div>