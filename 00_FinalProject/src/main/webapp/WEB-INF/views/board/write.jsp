<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<script>
function check() {
	if ($("input:checkbox[name='category']").is(":checked")==false) {
		alert("적어도 하나는 선택하여 주십시오.");
		return;
	}
}
function cate_hash(self){
	let checkboxes = document.getElementsByClassName("category");
	
	if (checkboxes.length > 0) {
		  for(var i=0; i<checkboxes.length; i++) {
			  checkbox = checkboxes[i];
			  if(checkbox.checked == true) {
				  console.log(0);
			  }else {
				  console.log(1);
				  
			  }
		  }
		  console.log($('.hashtags').val()+"#"+$(self).val());
		$('.hashtags').val($('.hashtags').val()+"#"+$(self).val());
		}
}




</script>
<div class="board_wrap">
	<h2>게시글 작성</h2>
	 <form id="writeform" method="post" enctype="multipart/form-data" action="${ctxPath }/board/writeform">
           <div class="form-group" align="left">
				<span>해시태그</span>
	            <input type="checkbox" id="life" onclick="cate_hash(this)" class="category" value="일상" checked><label for="review">일상</label>
				<input type="checkbox" id="review" onclick="cate_hash(this)" class="category" value="리뷰"><label for="review">리뷰</label>
				<input type="checkbox" id="food" onclick="cate_hash(this)" class="category" value="음식"><label for="food">음식</label>
				<input type="checkbox" id="equipment" onclick="cate_hash(this)" class="category" value="장비"><label for="equipment">장비</label>
           </div>
           <div class="form-group" align="left">
               <label for="content">내용:</label>
               <textarea class="form-control" rows="15" id="content" name="content"></textarea>
           </div>
           <div class="form-group" align="left">
               <label for="subject">파일:</label>
               <input type="file" class="form-control-file border" name="upfile" multiple="multiple">
           </div>
           <div>

           	<input type="text" class="hashtags" value="">

           </div>
           <script type="text/javascript">
           	
           </script>
           <div>
           	<button type="submit" class="btn btn-primary">글작성</button>
           	<button type="reset" class="btn btn-warning">초기화</button>
           </div>
           
       </form>
</div>