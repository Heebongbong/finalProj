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
						<input type="checkbox" style="display: none" value="#텐트#타프" id="cate_tent" name="category">
						<label for="cate_tent" onclick="checkbox_click(this)">텐트/타프</label>
					</li>
					<li class="obj_li">
						<input type="checkbox" style="display: none" value="#테이블#체어" id="cate_table" name="category">
						<label for="cate_table" onclick="checkbox_click(this)">테이블/체어</label>
					</li>
					<li class="obj_li">
						<input type="checkbox" style="display: none" value="#취사#수납" id="cate_cooking" name="category">
						<label for="cate_cooking" onclick="checkbox_click(this)">취사/수납</label>
					</li>
					<li class="obj_li">
						<input type="checkbox" style="display: none" value="#랜턴#조명" id="cate_lantern" name="category">
						<label for="cate_lantern" onclick="checkbox_click(this)">랜턴/조명</label>
					</li>
					<li class="obj_li">
						<input type="checkbox" style="display: none" value="#소품#기타" id="cate_etc" name="category">
						<label for="cate_etc" onclick="checkbox_click(this)">소품/기타</label>
					</li>
				</ul>
				
				<ul class="loc_ul">
				 	<li class="loc_li">
					    <input type="radio" style="display: none" name="category" id="option1" value="#서울">
					    <label for="option1" onclick="radio_click(this)">서울</label>
				  	</li>
				  	<li class="loc_li">
				    	<input type="radio" style="display: none" name="category" id="option2" value="#경기">
				   	 	<label for="option2" onclick="radio_click(this)">경기</label>
				  	</li>
				  	<li class="loc_li">
				    	<input type="radio" style="display: none" name="category" id="option3" value="#강원">
				    	<label for="option3" onclick="radio_click(this)">강원</label>
				 	</li>
				  	<li class="loc_li">
				    	<input type="radio" style="display: none" name="category" id="option4" value="#충청">
				    	<label for="option4" onclick="radio_click(this)">충청</label>
				  	</li>
				 	<li class="loc_li">
					    <input type="radio" style="display: none" name="category" id="option5" value="#전라">
					    <label for="option5" onclick="radio_click(this)">전라</label>
				  	</li>
				  	<li class="loc_li">
					    <input type="radio" style="display: none" name="category" id="option6" value="#경상">
					    <label for="option6" onclick="radio_click(this)">경상</label>
				  	</li>
				  	<li class="loc_li">	
					    <input type="radio" style="display: none" name="category" id="option7" value="#제주">
					    <label for="option7" onclick="radio_click(this)">제주</label>
				  	</li>
				</ul>
			</div>
		</div>
		
		<hr class="hr0">
		
		<div id="write_content">
			<div class="text">제 목</div>
			<input type="text" placeholder=" 제목을 입력하세요 " name="title" class="title_input">		
			
			<hr class="hr">
			
			<div class="text">내 용</div>		
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
		<input type="file" style="display: none;" id="files" class="form-control-file border" name="files" multiple="multiple" onchange="fileUpload()">
		<div class="img_container">
		</div>	
		
		<div class="bot_container">
			<input type="submit" class="btn-primary" value="글작성">
		</div>
	</form>
</div>