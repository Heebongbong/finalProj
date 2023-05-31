<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<c:set var="dto" value="${Map.get('BoardDTO') }"/>
<c:set var="hash" value="${Map.get('HashMap') }"/>
<c:set var="files" value="${Map.get('Files') }"/>
<div class="board_wrap">
	<h2>게시글 작성</h2>
	<form onsubmit="return check()" id="updateform" method="post" enctype="multipart/form-data" action="${ctxPath }/board/updateform">
		<div class="form-group" align="left">
			<span>해시태그</span>
			<input type="checkbox" id="life" onclick="cate_hash(this)" class="category" value="일상" <c:if test="${hash.get('일상') == 1 }">checked</c:if>><label for="life">일상</label>
			<input type="checkbox" id="food" onclick="cate_hash(this)" class="category" value="요리" <c:if test="${hash.get('요리') == 1 }">checked</c:if>><label for="food">요리</label>
			<input type="checkbox" id="equipment" onclick="cate_hash(this)" class="category" value="장비" <c:if test="${hash.get('장비') == 1 }">checked</c:if>><label for="equipment">장비</label>
			<c:if test="${hash.get('리뷰') == 1 }">
				<input type="checkbox" id="review" class="category" disabled="disabled" checked="checked"><label for="review">리뷰</label>
				<input type="hidden" value="#리뷰" name="category" readonly="readonly">
			</c:if>
		</div>
		<div class="form-group" align="left">
			<textarea class="form-control" rows="15" id="content" name="content">${dto.getContent() }</textarea>
		</div>
		<div class="form-group" align="left">
			<label for="upfile">/ 파일 추가 버튼 수정 /</label>
			<input type="file" style="display: none;" id="upfile" class="form-control-file border" name="upfile" multiple="multiple">
		</div>
		<div class="form-group" align="left">
			<span>존재하는 사진 파일</span>
			<div class="row">
				<c:forEach var="file" items="${files}" varStatus="state">
					<div class='upload_photo_file col-4' >
						<input id="${state.count }_checkbox" type="checkbox" name="deletefile" value=${file }>
						<label for="${state.count }_checkbox"><img src="${ctxPath }/resources/images/board/${dto.getPhoto_folder()}/${file }"></label>
					</div>
				</c:forEach>
			</div>
		</div>
		<div>
			<input type="text" class="hashtag" name="hashtag" value="<c:forEach items="${hash }" var="item"><c:if test="${item.getKey() != '리뷰' }">#${item.getKey() }</c:if></c:forEach>">
		</div>
		<div>
			<input type="hidden" name="cm_no" value="${dto.getCm_no() }">
			<input type="hidden" name="type" value="${dto.getType() }">
			<input type="hidden" name="email" value="${dto.getEmail() }">
			<button type="submit" class="btn btn-primary">글작성</button>
			<button type="reset" class="btn btn-warning">초기화</button>
		</div>
	</form>
</div>