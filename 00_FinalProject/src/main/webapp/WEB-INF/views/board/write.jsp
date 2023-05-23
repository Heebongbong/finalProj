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
</script>
<div class="board_wrap">

	<h2>게시글 작성</h2>

	 <form id="writeform" method="post" enctype="multipart/form-data" action="${ctxPath }/board/writeform">
           <div class="form-group" align="left">
            <input type="checkbox" id="life" name="category" value="일상"><label>일상</label>
			<input type="checkbox" id="review" name="category" value="리뷰"> 리뷰
			<input type="checkbox" id="food" name="category" value="음식"> 음식
			<input type="checkbox" id="equipment" name="category" value="장비"> 장비
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
           	<input type="text" name="hashtag">
           </div>
           <script type="text/javascript">
           	
           </script>
           <div>
           	<button type="submit" class="btn btn-primary">글작성</button>
           	<button type="reset" class="btn btn-warning">초기화</button>
           </div>
           
       </form>
</div>