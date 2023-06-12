<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<c:set var="dto" value="${Map.get('BoardDTO') }"/>
<c:set var="hash" value="${Map.get('HashMap') }"/>
<c:set var="files" value="${Map.get('Files') }"/>
<div class="board_wrap">
	<c:if test="${dto.getContent_id()==0 }">	
		<div id="title">커뮤니티 글 수정</div>
	</c:if>
	<c:if test="${dto.getContent_id()!=0 }">
		<div id="title">캠핑리뷰 수정</div>
	</c:if>
	<form onsubmit="return check()" id="updateform" method="post" enctype="multipart/form-data" action="${ctxPath }/board/updateform">
	
		<div class="text">카테고리</div>
		
		<ul class='write_side_ul'>
			<li class="write_side_li<c:if test="${hash.get('일상') == 1 }"> checked</c:if>">
				<input type="checkbox" style="display: none" id="life" name="category" value="#일상" <c:if test="${hash.get('일상') == 1 }">checked</c:if>>
				<label for="life" onclick="checkbox_click(this)">일상</label>
			</li>
			<li class="write_side_li<c:if test="${hash.get('요리') == 1 }"> checked</c:if>">
				<input type="checkbox" style="display: none" id="food" name="category" value="#요리" <c:if test="${hash.get('요리') == 1 }">checked</c:if>>
				<label for="food" onclick="checkbox_click(this)">요리</label>
			</li>
			<li class="write_side_li<c:if test="${hash.get('장비') == 1 }"> checked</c:if>">
				<input type="checkbox" style="display: none" id="equipment" name="category" value="#장비" <c:if test="${hash.get('장비') == 1 }">checked</c:if>>
				<label for="equipment" onclick="checkbox_click(this)">장비</label>
			</li>
			<c:if test="${dto.getContent_id()!=0 }">
				<li class="write_side_li checked">
					<input type="checkbox" style="display: none" id="review" name="category" value="#리뷰" disabled="disabled" checked="checked">
					<label for="review">리뷰</label>
				</li>
			</c:if>
		</ul>
		
		<hr class="hr">
		
		<div class="text">내 용</div>
		
		<textarea class="form-control" rows="15" name="content">${dto.getContent() }</textarea>
			
		<hr class="hr">
		
		<div class="text">해쉬태그</div>
		<input type="text" class="hashtag" name="hashtag" value="<c:forEach items="${hash }" var="item"><c:if test="${ item.getKey() != '일상' && (item.getKey() != '요리') && (item.getKey() != '장비') && (item.getKey() != '리뷰') && (item.getKey() != '')}">#${item.getKey() }</c:if></c:forEach>">

		<hr class="hr">
		
		<div class="bottom-container">
			<div class="img_label_wrap">
				<label for="upfile" class="upload_text">사진첨부</label>
				<label for="upfile" class="upload_text">			
					<img src="${ctxPath }/resources/images/icon/camera.png">
				</label>
			</div>
			<input type="file" style="display: none;" id="upfile" class="form-control-file border" name="upfile" multiple="multiple" onchange="fileUpload()">
			<div class="img_container">
			</div>
			
			<div class="text none_file">삭제할 사진 선택</div>
			<div class="row">
				<c:forEach var="file" items="${files}" varStatus="state">
					<div class='upload_photo_file' >
						<input id="${state.count }_checkbox" type="checkbox" name="deletefile" value=${file }>
						<label for="${state.count }_checkbox">
							<img src="${ctxPath }/resources/images/board/${dto.getPhoto_folder()}/${file }">
						</label>
					</div>
				</c:forEach>
			</div>
		</div>
			
		<div class="button-container">
			<input type="hidden" name="cm_no" value="${dto.getCm_no() }">
			<input type="hidden" name="type" value="${dto.getType() }">
			<input type="hidden" name="email" value="${dto.getEmail() }">
			<button type="submit" class="btn btn-primary">글작성</button>
			<button type="reset" class="btn btn-primary">초기화</button>
		</div>
	</form>
</div>