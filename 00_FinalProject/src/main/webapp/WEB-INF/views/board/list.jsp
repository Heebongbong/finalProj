<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<c:set value="<%=request.getContextPath() %>" var="ctxPath"/>
<c:set value="${sessionScope.LoginUser }" var="loginUser"/>
<c:set value="${UserList }" var="boardList"/>
<c:set value="${BoardList }" var="boardList"/>
<c:set value="${Photoes }" var="photoes"/>
<link rel="stylesheet" href="${ctxPath }/resources/css/slick/slick.css">
<link rel="stylesheet" href="${ctxPath }/resources/css/slick/slick-theme.css">
<div id="list_wrap">
	<ul class="list_navi">
		<li class="list_navi_li">
			<a href="${ctxPath }/board/list">전체목록</a>
		</li>
		<li class="list_navi_li">
			<a href="${ctxPath }/board/list?keyword=요리">요리</a>
		</li>
		<li class="list_navi_li">
			<a href="${ctxPath }/board/list?keyword=일상">일상</a>
		</li>
		<li class="list_navi_li">
			<a href="${ctxPath }/board/list?keyword=캠핑">캠핑</a>
		</li>
		<li class="list_navi_li">
			<a href="${ctxPath }/board/list?keyword=리뷰">리뷰</a>
		</li>
	</ul>
	<c:if test="${!empty boardList }">
		<c:forEach items="${boardList }" var="board">
			<div>
				<c:set value="${board.getPhoto_files() }" var="files"/>
				<p>${board.getCm_no() }</p>
				<p>${board.getUser_no() }</p>
				<p>${board.getContent() }</p>
				<p>${board.getHashtag() }</p>
				<c:if test="${!empty files }">
					<c:forEach items="${files }" var="file">
					<img src="${ctxPath }/resources/images/board/${board.email}/${files}/${file}.jpg">
				</c:forEach>
				</c:if>
				<c:if test="${empty files }">
					<p>파일이 없습니다.</p>
				</c:if>
				
			</div>
		</c:forEach>
	</c:if>
</div>