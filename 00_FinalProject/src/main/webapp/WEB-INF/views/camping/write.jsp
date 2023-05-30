<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${sessionScope.LoginUser }" var="loginUser" />
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<div class="board_wrap">
	<h2>게시글 작성</h2>
	<form onsubmit="return check()" id="writeform" method="post" enctype="multipart/form-data" action="${ctxPath }/board/review/writeform">
	 	<input type="hidden" name="user_no" value="${loginUser.user_no }">
        <div class="form-group" align="left">
			<span>해시태그</span>
	        <input type="checkbox" id="life" onclick="cate_hash(this)" class="category" name="category" value="일상"><label for="review">일상</label>
			<input type="checkbox" id="food" onclick="cate_hash(this)" class="category" name="category" value="요리"><label for="food">요리</label>
			<input type="checkbox" id="equipment" onclick="cate_hash(this)" class="category" name="category" value="장비"><label for="equipment">장비</label>
			<input type="checkbox" id="review" class="category" name="category" readonly="readonly" checked="checked" value="리뷰"><label for="review">리뷰</label>
        </div>
        <div class="form-group" align="left">
            <label for="content">내용:</label>
            <!-- 캠핑장 정보 -->
            <textarea class="form-control" rows="15" id="content" name="content"></textarea>
        </div>
        <div class="form-group" align="left">
        	<label for="subject">파일:</label>
            <input type="file" class="form-control-file border" name="upfile" multiple="multiple">
        </div>
        <div>
           	<input type="text" class="hashtag" name="hashtag" value="">
        </div>
        <div>
           	<button type="submit" class="btn btn-primary">글작성</button>
           	<button type="reset" class="btn btn-warning">초기화</button>
        </div>
     </form>
</div>