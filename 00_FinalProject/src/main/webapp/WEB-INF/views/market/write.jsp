<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<div class="market_write_wrap">
	<form onsubmit="return check()" id="writeform" method="post" enctype="multipart/form-data" action="${ctxPath }/market/writeform">
		<div class="form-group" align="left">
			<div>
				<input type="checkbox" value="#텐트#타프" id="cate_tent" name="category"><label for="cate_tent">텐트/타프</label>
				<input type="checkbox" value="#테이블#체어" id="cate_table" name="category"><label for="cate_table">테이블/체어</label>
				<input type="checkbox" value="#취사#수납" id="cate_cooking" name="category"><label for="cate_cooking">취사/수납</label>
				<input type="checkbox" value="#랜턴#조명" id="cate_lantern" name="category"><label for="cate_lantern">랜턴/조명</label>
				<input type="checkbox" value="#소품#기타" id="cate_etc" name="category"><label for="cate_etc">소품/기타</label>
			</div>
			<div>
				<select name="category">
					<option value="" disabled="disabled">::지역 선택::</option>
					<option value="#서울">서울</option>
					<option value="#경기">경기</option>
					<option value="#강원">강원</option>
					<option value="#충청">충청</option>
					<option value="#전라">전라</option>
					<option value="#경상">경상</option>
					<option value="#제주">제주</option>
				</select>
				<input type="text" placeholder="제목 : " name="title">
			</div>
		</div>
		<div class="form-group" align="left">
			<textarea class="form-control" rows="15" name="content" placeholder="내용 : "></textarea><br>
			<input type="text" name="price" placeholder="가격 : ">
		</div>
		<div class="form-group" align="left">
			<label for="files">/ 파일 추가 버튼 /</label>
			<input type="file" style="display: none;" id="files" class="form-control-file border" name="files" multiple="multiple">
		</div>
		<div>
			<input type="text" name="hashtag" class="hashtag" value="#팝니다">
		</div>
		<div>
			<input type="submit" class="" value="글작성">
			<input type="reset" class="" value="초기화">
		</div>
	</form>
</div>