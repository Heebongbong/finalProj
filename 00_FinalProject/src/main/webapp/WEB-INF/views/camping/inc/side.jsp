<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<div id="camping_side">
	<ul class='camping_side_ul'>
		<li>
			<a href="${ctxPath }/camping/camping?keyword=서울">서울</a>
		</li>
		<li>
			<a href="${ctxPath }/camping/camping?keyword=경기">경기</a>
		</li>
		<li>
			<a href="${ctxPath }/camping/camping?keyword=인천">인천</a>
		</li>
		<li>
			<a href="${ctxPath }/camping/camping?keyword=강원">강원</a>
		</li>
		<li>
			<a href="${ctxPath }/camping/camping?keyword=경북&keyword2=경상북도">경북</a>
		</li>
		<li>
			<a href="${ctxPath }/camping/camping?keyword=경남&keyword2=경상남도">경남</a>
		</li>
		<li>
			<a href="${ctxPath }/camping/camping?keyword=충북&keyword2=충청북도">충북</a>
		</li>
		<li>
			<a href="${ctxPath }/camping/camping?keyword=충남&keyword2=충청남도">충남</a>
		</li>
		<li>
			<a href="${ctxPath }/camping/camping?keyword=전북&keyword2=전라북도">전북</a>
		</li>
		<li>
			<a href="${ctxPath }/camping/camping?keyword=전남&keyword2=전라남도">전남</a>
		</li>
		<li>
			<a href="${ctxPath }/camping/camping?keyword=제주">제주</a>
		</li>
		<li>
			<a href="${ctxPath }/camping/camping?keyword=광주">광주</a>
		</li>
		<li>
			<a href="${ctxPath }/camping/camping?keyword=대구">대구</a>
		</li>
		<li>
			<a href="${ctxPath }/camping/camping?keyword=대전&keyword2=세종">대전/세종</a>
		</li>
		<li>
			<a href="${ctxPath }/camping/camping?keyword=울산&keyword2=부산">울산/부산</a>
		</li>
	</ul>
	
	<div class="side_search">
		<input type="text" placeholder="검색어 입력" id="locs">
		<input type="button" value="검색" onclick="locSearch()">
	</div>
</div>