<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${sessionScope.LoginUser }" var="loginUser" />
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<div class="board_wrap">
	<h2>게시글 작성</h2>
	<form onsubmit="return check()" id="writeform" method="post" enctype="multipart/form-data" action="${ctxPath }/board/writeform">
        <div class="form-group" align="left">
			<span>해시태그</span>
	        <input type="checkbox" id="life" class="category" name="category" value="#일상"><label for="life">일상</label>
			<input type="checkbox" id="food" class="category" name="category" value="#요리"><label for="food">요리</label>
			<input type="checkbox" id="equipment" class="category" name="category" value="#장비"><label for="equipment">장비</label>
			<c:if test="${!empty Content_id }">
				<input type="checkbox" id="review" class="category" disabled="disabled" checked="checked"><label for="review">리뷰</label>
				<input type="hidden" name="content_id" value="${Content_id }">
				<input type="hidden" value="#리뷰" name="category" readonly="readonly">
			</c:if>
		</div>
		<div class="form-group" align="left">
			<textarea class="form-control" rows="15" id="content" name="content" placeholder="내용 : "></textarea>
		</div>
		<div class="form-group" align="left">
			<label for="upfile">/ 파일 버튼 /</label>
			<input type="file" style="display: none" id="upfile" class="form-control-file border" name="upfile" multiple="multiple">
		</div>
		<div>
			<input type="text" class="hashtag" name="hashtag" value="#">
		</div>
		<div>
			<button type="submit" class="btn btn-primary">글작성</button>
			<button type="reset" class="btn btn-warning">초기화</button>
		</div>
	</form>
</div>