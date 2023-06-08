<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${sessionScope.LoginUser }" var="loginUser" />
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<div class="board_wrap">
	<div id="title">커뮤니티 글쓰기</div>
	<form onsubmit="return check()" id="writeform" method="post" enctype="multipart/form-data" action="${ctxPath }/board/writeform">
		<div class="text">해시태그</div>
		
		<ul class='write_side_ul'>
			<li>
				<input type="checkbox" style="display: none" id="life" class="category" name="category" value="#일상" onchange="checkbox(this)"><label for="life">일상</label>
			</li>
			<li>
				<input type="checkbox" style="display: none" id="food" class="category" name="category" value="#요리" onchange="checkbox(this)"><label for="food">요리</label>
			</li>
			<li>
				<input type="checkbox" style="display: none" id="equipment" class="category" name="category" value="#장비" onchange="checkbox(this)"><label for="equipment">장비</label>
			</li>
		</ul>
		
		<hr>
        
        <div class="text">내 용</div>
        
		<c:if test="${!empty Content_id }">
			<input type="checkbox" id="review" class="category" disabled="disabled" checked="checked"><label for="review">리뷰</label>
			<input type="hidden" name="content_id" value="${Content_id }">
			<input type="hidden" value="#리뷰" name="category" readonly="readonly">
		</c:if>
		
		<textarea class="form-control" rows="10" id="content" name="content" placeholder=" 내용을 입력하세요 "></textarea>
		
		<hr>
		
		<div class="bottom-container">
			<div class="text">
				<label for="upfile" >사진첨부</label>
				<input type="file" style="display: none" id="upfile" class="form-control-file border" name="upfile" multiple="multiple">
			</div>
			<div class="hash">
				<input type="text" class="hashtag" name="hashtag" value="#">
			</div>
		
		
			<button type="submit" class="btn btn-primary">글작성</button>
			<!-- <button type="reset" class="btn btn-warning">초기화</button> -->
		 </div>
	</form>
</div>