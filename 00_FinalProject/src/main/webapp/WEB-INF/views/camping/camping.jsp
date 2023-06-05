<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<div id="camping_wrap">
	
	<div id="camping_side">
		<ul class='camping_side_ul'>
			<li>
				<a href="javascript:locSearch(서울)">서울</a>
			</li>
			<li>
				<a href="javascript:locSearch(경기)">경기도</a>
			</li>
			<li>
				<a href="javascript:locSearch(강원)">강원도</a>
			</li>	
			<li>
				<a href="javascript:locSearch(경상)">경상도</a>
			</li>
			<li>
				<a href="javascript:locSearch(충청)">충청도</a>
			</li>
			<li>
				<a href="javascript:locSearch(전라)">전라도</a>
			</li>
			<li>
				<a href="javascript:locSearch(제주)">제주도</a>
			</li>
		</ul>
		
		<div class="side_search">
			<input type="text" placeholder="캠핑장 검색" id="locs" class="search_camp" value="${Keyword }">
			<button type="submit" onclick="locSearch()"><i class="fa fa-search" aria-hidden="true"></i></button>
		</div>
			
	</div>

<input type="hidden" value="${Category }" id="hidden_category">
	<div id="camping_search_wrap">
		<%-- <c:forEach items="${CampingList }" var="dto">
			<div class="camping_list_wrap">
				<div class="camping_list_img">
					<img onclick="location.href='${ctxPath }/camping/details?content_id=${dto.getContent_id() }'" src="${dto.getFirstImageUrl() }" alt="캠핑장 대표 이미지" class="camping_image">
				</div>
				<div class="camping_list_info">
					<input type="hidden" value="${dto.getContent_id() }" class="content_id">
					<div class="camp_head">
						<p class="camp_title"><a href="${ctxPath }/camping/details?content_id=${dto.getContent_id() }" class="camping_title">${dto.getFacltNm() }</a></p>
						<p class="camp_addr">${dto.getAddr1() }</p>
					</div>
					<div class="camp_tag">
						<p>
							<c:if test="${dto.animalCmgCl eq 1}">#애견동반</c:if>
							<c:if test="${not empty dto.posblFcltyCl}">#${dto.posblFcltyCl}</c:if>
							<c:if test="${not empty dto.exprnProgrm}">#${dto.exprnProgrm}</c:if>
							<c:if test="${not empty dto.themaEnvrnCl}">#${dto.themaEnvrnCl}</c:if>
						</p>
					</div>
				</div>
			</div>
		</c:forEach> --%>
	</div>
</div>
