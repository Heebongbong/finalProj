<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<c:set var="dto" value="${Map.get('BoardDTO') }"/>
<c:set var="hash" value="${Map.get('HashMap') }"/>
<c:set var="files" value="${Map.get('Files') }"/>
<div class="board_wrap">
	<div id="title">게시글 수정</div>
	<form onsubmit="return check()" id="updateform" method="post" enctype="multipart/form-data" action="${ctxPath }/board/updateform">
	
		<div class="text">해시태그</div>
		
		<ul class='write_side_ul'>
			<li class="<c:if test="${hash.get('일상') == 1 }">checked</c:if>">
				<input type="checkbox" style="display: none" id="life" onclick="cate_hash(this)" class="category" value="일상" onchange="checkbox(this)" <c:if test="${hash.get('일상') == 1 }">checked</c:if>><label for="life">일상</label>
			</li>
			<li class="<c:if test="${hash.get('요리') == 1 }">checked</c:if>">
				<input type="checkbox" style="display: none" id="food" onclick="cate_hash(this)" class="category" value="요리" onchange="checkbox(this)" <c:if test="${hash.get('요리') == 1 }">checked</c:if>><label for="food">요리</label>
			</li>
			<li class="<c:if test="${hash.get('장비') == 1 }">checked</c:if>">
				<input type="checkbox" style="display: none" id="equipment" onclick="cate_hash(this)" class="category" value="장비" onchange="checkbox(this)" <c:if test="${hash.get('장비') == 1 }">checked</c:if>><label for="equipment">장비</label>
			</li>
			<c:if test="${hash.get('리뷰') == 1 }">
				<li class="<c:if test="${hash.get('리뷰') == 1 }">checked</c:if>">
					<input type="checkbox" style="display: none" id="review" class="category" disabled="disabled" onchange="checkbox(this)" checked="checked"><label for="review">리뷰</label>
					<input type="hidden" value="#리뷰" name="category" readonly="readonly">
				</li>
			</c:if>
		</ul>
		
		<hr>
		
		<div class="text">내 용</div>
		
		<textarea class="form-control" rows="15" id="content" name="content">${dto.getContent() }</textarea>
			
		<hr>
		
		<div class="bottom-container"> 	
			<label for="upfile" class="text">첨부파일</label>
			<div>
				<input type="file" style="display: none;" id="upfile" class="form-control-file border" name="upfile" multiple="multiple" onchange="fileUpload()">
			</div>
			<div class="row">
				<c:forEach var="file" items="${files}" varStatus="state">
					<div class='upload_photo_file col-4' >
						<input id="${state.count }_checkbox" type="checkbox" name="deletefile" value=${file }>
						<label for="${state.count }_checkbox"><img src="${ctxPath }/resources/images/board/${dto.getPhoto_folder()}/${file }"></label>
					</div>
				</c:forEach>
			</div>
			<div class="hash">
				<input type="text" class="hashtag" name="hashtag" value="<c:forEach items="${hash }" var="item"><c:if test="${item.getKey() != '리뷰' }">#${item.getKey() }</c:if></c:forEach>">
			</div>
			
			<input type="hidden" name="cm_no" value="${dto.getCm_no() }">
			<input type="hidden" name="type" value="${dto.getType() }">
			<input type="hidden" name="email" value="${dto.getEmail() }">
		</div>
		
		<div class="button-container">			
			<button type="submit" class="btn btn-primary">글작성</button>
			<button type="reset" class="btn btn-primary">초기화</button>
		</div>
	</form>
</div>