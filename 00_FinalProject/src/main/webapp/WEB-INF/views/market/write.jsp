<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<div class="market_write_wrap">
	<form onsubmit="return check()" id="writeform" method="post" enctype="multipart/form-data" action="${ctxPath }/market/writeform">
		
		<div id="title">마켓 글쓰기</div>
		
		<div class="text">카테고리</div>
		
		<div class="market_navi">
			<div class="market_navi_cate">
				<a>상품별</a>
				<a>지역별</a>
			</div>
			<div class="market_navi_cont">
				<ul class="obj_ul">
					<li class="obj_li">
						<input type="radio" style="display: none" value="#텐트#타프" id="cate_tent" name="category" onchange="radiobox(this)"><label for="cate_tent">텐트/타프</label>
					</li>
					<li class="obj_li">
						<input type="radio" style="display: none" value="#테이블#체어" id="cate_table" name="category" onchange="radiobox(this)"><label for="cate_table">테이블/체어</label>
					</li>
					<li class="obj_li">
						<input type="radio" style="display: none" value="#취사#수납" id="cate_cooking" name="category" onchange="radiobox(this)"><label for="cate_cooking">취사/수납</label>
					</li>
					<li class="obj_li">
						<input type="radio" style="display: none" value="#랜턴#조명" id="cate_lantern" name="category" onchange="radiobox(this)"><label for="cate_lantern">랜턴/조명</label>
					</li>
					<li class="obj_li">
						<input type="radio" style="display: none" value="#소품#기타" id="cate_etc" name="category" onchange="radiobox(this)"><label for="cate_etc">소품/기타</label>
					</li>
				</ul>
				
				<ul class="loc_ul">
				 	<li class="loc_li">
					    <input type="radio" style="display: none" name="category" id="option1" value="#서울" onchange="radioloc(this)">
					    <label for="option1">서울</label>
				  	</li>
				  	<li class="loc_li">
				    	<input type="radio" style="display: none" name="category" id="option2" value="#경기" onchange="radioloc(this)">
				   	 	<label for="option2">경기</label>
				  	</li>
				  	<li class="loc_li">
				    	<input type="radio" style="display: none" name="category" id="option3" value="#강원" onchange="radioloc(this)">
				    	<label for="option3">강원</label>
				 	</li>
				  	<li class="loc_li">
				    	<input type="radio" style="display: none" name="category" id="option4" value="#충청" onchange="radioloc(this)">
				    	<label for="option4">충청</label>
				  	</li>
				 	<li class="loc_li">
					    <input type="radio" style="display: none" name="category" id="option5" value="#전라" onchange="radioloc(this)">
					    <label for="option5">전라</label>
				  	</li>
				  	<li class="loc_li">
					    <input type="radio" style="display: none" name="category" id="option6" value="#경상" onchange="radioloc(this)">
					    <label for="option6">경상</label>
				  	</li>
				  	<li class="loc_li">	
					    <input type="radio" style="display: none" name="category" id="option7" value="#제주" onchange="radioloc(this)">
					    <label for="option7">제주</label>
				  	</li>
				</ul>
			</div>
		</div>
		
		<hr class="hr0">
		
		
		<div id="write_content">
			<div class="text">제목</div>
			<input type="text" placeholder=" 제목을 입력하세요 " name="title" class="title_input">		
			
			<hr class="hr">
			
			<div class="text">내용</div>		
			<textarea class="form-control" rows="7" name="content" placeholder=" 내용을 입력하세요  "></textarea>
			<div class="price_wrap">
				<input type="text" name="price" placeholder=" 가격을 입력하세요 (숫자만 입력) " class="price">
			</div>
			<hr class="hr">
			
			<div class="text">해쉬태그</div>
			<input type="text" name="hashtag" class="hashtag" value="#팝니다">
			<hr class="hr">
			
		</div>
		
		<label for="files" class="label">
			사진첨부
		</label>
		<label for="files" class="label">
			<img src="${ctxPath }/resources/images/icon/camera.png">
		</label>
		<div class="img_container">
			<input type="file" style="display: none;" id="files" class="form-control-file border" name="files" multiple="multiple" onchange="fileUpload()">
		</div>	
		
		<div class="bot_container">
			<input type="submit" class="btn-primary" value="글작성">
		</div>
		
		
		
		
		<!-- <div class="form-group" align="left">
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
		</div> -->
	</form>
</div>