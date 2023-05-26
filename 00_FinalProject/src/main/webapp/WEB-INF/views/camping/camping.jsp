<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<div id="camping_wrap">
<input type="hidden" value="${keyword }" id="hidden_keyword">
<input type="hidden" value="${keyword2 }" id="hidden_keyword2">
	<div id="camping_search_wrap">
		<c:forEach items="${CampingList }" var="dto">
		<div class="camping_list_wrap">
			<div class="camping_list_img">
				<img src="${dto.getFirstImageUrl() }" alt="캠핑장 대표 이미지" class="camping_image">
			</div>
			<div class="camping_list_info">
				<input type="hidden" value="${dto.getContent_id() }" class="content_id">
				<p>이름:<a href="${ctxPath }/camping/details?content_id=${dto.getContent_id() }" class="camping_title">${dto.getFacltNm() }</a> </p>
				<p>소개: ${dto.getLineIntro() }</p>
				<p>홈페이지: <a href="${dto.getHomepage() }" target="_blank">${dto.getHomepage() }</a></p>
				<p>시설 : ${dto.getInduty() }</p>
				<p>주소 : ${dto.getAddr1() }</p>
			</div>
		</div>
		</c:forEach>
		
	</div>


</div>

