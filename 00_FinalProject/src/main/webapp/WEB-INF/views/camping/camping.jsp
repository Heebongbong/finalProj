<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<div id="camping_wrap">
<input type="hidden" value="${keyword }" id="hidden_keyword">
<input type="hidden" value="${keyword2 }" id="hidden_keyword2">
	<div id="camping_search_wrap">
		<c:forEach items="${CampingList }" var="dto">
		<div id="camping">
			<div id="camping_content">
				<img src="${dto.getFirstImageUrl() }" alt="no image" class="camping_image">
			
				<div id="camping_content_info">
					<input type="hidden" value="${dto.getContent_id() }" class="content_id">
					<p>이름:<a href="${ctxPath }/camping/details?content_id=${dto.getContent_id() }">${dto.getFacltNm() }</a> </p>
					<p>소개: ${dto.getLineIntro() }</p>
					<p>홈페이지: <a href="${dto.getHomepage() }" target="_blank">${dto.getHomepage() }</a></p>
					<p>시설 : ${dto.getInduty() }</p>
					<p>주소 : ${dto.getAddr1() }</p>
				</div>
			</div>
		</div>
		</c:forEach>
	</div>


</div>

