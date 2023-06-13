<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${sessionScope.LoginUser }" var="loginUser" />
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<div class="board_wrap">
	<c:if test="${!empty Content_id }">
		<div id="title"><img alt="" src="${ctxPath }/resources/images/icon/camp_icon.png"></div>
	</c:if>
	<c:if test="${empty Content_id }">
		<div id="title"><img alt="" src="${ctxPath }/resources/images/icon/board_icon.png"></div>
	</c:if>
	<form onsubmit="return check()" id="writeform" method="post" enctype="multipart/form-data" action="${ctxPath }/board/writeform">
		<div class="text">카테고리</div>
		<ul class='write_side_ul'>
			<li class='write_side_li'>
				<input type="checkbox" style="display: none" id="life" class="category" name="category" value="#일상">
				<label for="life" onclick="checkbox_click(this)">일상</label>
			</li>
			<li class='write_side_li'>
				<input type="checkbox" style="display: none" id="food" class="category" name="category" value="#요리">
				<label for="food" onclick="checkbox_click(this)">요리</label>
			</li>
			<li class='write_side_li'>
				<input type="checkbox" style="display: none" id="equipment" class="category" name="category" value="#장비">
				<label for="equipment" onclick="checkbox_click(this)">장비</label>
			</li>
			<c:if test="${!empty Content_id }">
			<li class='write_side_li checked'>
				<input type="checkbox" style="display: none" id="review" class="category" name="category" disabled="disabled" checked="checked">
				<label for="review">리뷰</label>
				<input type="hidden" name="content_id" value="${Content_id }">
			</li>
			</c:if>
		</ul>
		
		<hr class="hr">
        
        <div class="text">내 용</div>
		<textarea class="form-control" rows="10" name="content" placeholder=" 내용을 입력하세요 "></textarea>
		<hr class="hr">
		
		<div class="text">해쉬태그</div>
		<input type="text" name="hashtag" class="hashtag" value="#">
		<hr class="hr">
		
		<label for="upfile" class="upload_text">사진첨부</label>
		<label for="upfile" class="upload_text">
			<img src="${ctxPath }/resources/images/icon/camera.png">
		</label>
		<input type="file" style="display: none" id="upfile" class="form-control-file border" name="upfile" multiple="multiple" onchange="fileUpload()">
		<div class="img_container"></div>
		
		<div class="bot_container">
			<button type="submit" class="btn btn-primary">글작성</button>
		</div>
			
	</form>
</div>
