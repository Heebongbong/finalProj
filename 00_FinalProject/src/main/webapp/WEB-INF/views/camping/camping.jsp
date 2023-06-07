<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<div id="camping_wrap">
	
	<div id="camping_side">
		<ul class='camping_side_ul'>
			<li>
				<a href="javascript:locSearch('서울')">서울</a>
			</li>
			<li>
				<a href="javascript:locSearch('경기')">경기도</a>
			</li>
			<li>
				<a href="javascript:locSearch('강원')">강원도</a>
			</li>	
			<li>
				<a href="javascript:locSearch('경상')">경상도</a>
			</li>
			<li>
				<a href="javascript:locSearch('충청')">충청도</a>
			</li>
			<li>
				<a href="javascript:locSearch('전라')">전라도</a>
			</li>
			<li>
				<a href="javascript:locSearch('제주')">제주도</a>
			</li>
		</ul>
		
		<div class="side_search">
			<input type="text" placeholder="캠핑장 검색" id="locs" class="search_camp" value="${C_Keyword }">
			<button type="button" onkeydown="if( event.keyCode == 13 ){locSearch();}" onclick="locSearch()"><i class="fa fa-search" aria-hidden="true"></i></button>
		</div>
			
	</div>
	<input type="hidden" value="${Category }" id="hidden_category">
	<div id="camping_search_wrap">
		
	</div>
</div>
