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

function loc_hash(){
	$('.hashtags').val($('.hashtags').val()+"#"+$('#subject').val());
}
</script>
<div class="market_write_wrap">
	<form id="writeform" method="post" enctype="multipart/form-data" action="${ctxPath }/market/writeform">
		<div class="form-group" align="left">
			<select id="subject" onchange="loc_hash()">
				<option value="서울">서울</option>
				<option value="경기">경기</option>
				<option value="강원">강원</option>
				<option value="충청">충청</option>
				<option value="전라">전라</option>
				<option value="경상">경상</option>
				<option value="제주">제주</option>
			</select>
			<input type="text" placeholder="제목 : " name="title">
		</div>
		<div class="form-group" align="left">
			<textarea class="form-control" rows="15" name="content" placeholder="내용 : "></textarea>
			<input type="text" name="price" placeholder="가격 : ">
		</div>
		<div class="form-group" align="left">
			<label for="subject">파일:</label>
			<input type="file" class="form-control-file border" name="upfile" multiple="multiple">
		</div>
		<div>
			<textarea rows="" cols="" name="hashtags" class="hashtags"></textarea>
		</div>
		<div>
			<input type="submit" class="" value="글작성">
			<input type="reset" class="" value="초기화">
		</div>
	</form>
</div>