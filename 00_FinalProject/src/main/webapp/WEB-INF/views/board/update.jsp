<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<c:set var="dto" value="${Map.get('BoardDTO') }"/>
<c:set var="hash" value="${Map.get('HashMap') }"/>
<c:set var="files" value="${Map.get('Files') }"/>
<div class="board_wrap">
	<form onsubmit="return check()" id="updateform" method="post" enctype="multipart/form-data" action="${ctxPath }/board/updateform">
		<div>
			<span>카테고리</span>
			<input type="checkbox" id="life" onclick="cate_hash(this)" class="category" name="category" value="일상" <c:if test="${hash.get('일상') == 1 }">checked</c:if>><label for="review">일상</label>
			<input type="checkbox" id="food" onclick="cate_hash(this)" class="category" name="category" value="요리" <c:if test="${hash.get('요리') == 1 }">checked</c:if>><label for="food">요리</label>
			<input type="checkbox" id="equipment" onclick="cate_hash(this)" class="category" name="category" value="장비" <c:if test="${hash.get('장비') == 1 }">checked</c:if>><label for="equipment">장비</label>
		</div>
		<div class="form-group" align="left">
			<textarea class="form-control" rows="15" id="content" name="content">${dto.getContent() }</textarea>
		</div>
		<div class="form-group" align="left">
			<label for="form-control-file">파일:</label>
			<input type="file" class="form-control-file border" name="upfile" multiple="multiple" id="form-control-file">
		</div>
		<div class="form-group" align="left">
			<p>삭제할 파일을 선택하세요.</p>
			<div class="row">
				<c:forEach var="file" items="${files}">
					<div class='upload_photo_file col-4' >
						<input id="${file }" type="checkbox" name=deletefile value="${file }">
						<label for="${file }"><img src="/finproj/resources/images/board/${dto.getPhoto_folder()}/${file }"></label>
					</div>
				</c:forEach>
			</div>
			<div>
				<input type="text" class="hashtag" name="hashtag" value="${dto.getHashtag() }">
			</div>
		</div>
		<div>
			<input type="hidden" name="cm_no" value="${dto.getCm_no() }">
			<button type="submit" class="btn btn-primary">수정</button>
			<button type="reset" class="btn btn-warning">초기화</button>
		</div>
	</form>
</div>