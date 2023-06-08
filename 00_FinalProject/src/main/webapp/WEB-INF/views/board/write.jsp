<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${sessionScope.LoginUser }" var="loginUser" />
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<script type="text/javascript">
	function fileUpload(){
		let fileInput = document.getElementById("upfile").files;
		console.log(1);
			if( fileInput.length > 0 ){
				console.log(2);
				for( var j = 0; j < fileInput.length; j++ ){
					console.log(fileInput[j].name); // 파일명 출력
					var fileOutput = document.createElement("p");
	                fileOutput.innerHTML = fileInput[j].name; // 파일명을 <p> 요소에 설정
	                document.getElementById("upfile").parentNode.appendChild(fileOutput); // <input> 요소의 부모 요소에 <p> 요소 추가
				}
			}

	}
</script>
<div class="board_wrap">
	<c:if test="${!empty Content_id }">
		<div id="title">캠핑리뷰</div>
	</c:if>
	<c:if test="${empty Content_id }">
		<div id="title">커뮤니티 글쓰기</div>
	</c:if>
	<form onsubmit="return check()" id="writeform" method="post" enctype="multipart/form-data" action="${ctxPath }/board/writeform">
		<div class="text">카테고리</div>
		
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
			<c:if test="${!empty Content_id }">
			<li>
				<input type="checkbox" style="display: none" id="review" class="category" disabled="disabled" checked="checked"><label for="review">리뷰</label>
				<input type="hidden" name="content_id" value="${Content_id }">
				<input type="hidden" value="#리뷰" name="category" readonly="readonly">
			</li>
		</c:if>
		</ul>
		
		<hr>
        
        <div class="text">내 용</div>
        
		
		
		<textarea class="form-control" rows="10" id="content" name="content" placeholder=" 내용을 입력하세요 "></textarea>
		
		<hr>
		
		<label for="upfile" class="upload_text">사진첨부</label>

		<div class="img_container">
			<input type="file" style="display: none" id="upfile" class="form-control-file border" name="upfile" multiple="multiple" onchange="fileUpload()">
		</div>
			
		<div class="bot_container">
			<input type="text" class="hashtag" name="hashtag" value="#">
			<button type="submit" class="btn btn-primary">글작성</button>
		</div>
			
	</form>
</div>
