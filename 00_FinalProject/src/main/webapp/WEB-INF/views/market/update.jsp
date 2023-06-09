<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<c:set var="dto" value="${Map.get('BoardDTO') }"/>
<c:set var="hash" value="${Map.get('HashMap') }"/>
<c:set var="files" value="${Map.get('Files') }"/>
<div class="market_write_wrap">
	<form onsubmit="return check()" id="updateform" method="post" enctype="multipart/form-data" action="${ctxPath }/market/updateform">
		
		<div id="title">마켓 글수정</div>
		
		<div class="text">카테고리</div>
		
		<div class="market_navi">
			<div class="market_navi_cate">
				<a>상품별</a>
				<a>지역별</a>
			</div>
			<div class="market_navi_cont">
				<ul class="obj_ul">
					<li class="obj_li <c:if test="${hash.get('텐트') == 1 || hash.get('타프') == 1 }">checked</c:if>">
						<input type="checkbox" style="display: none" name="category" value="#텐트#타프" id="cate_tent" <c:if test="${hash.get('텐트') == 1 || hash.get('타프') == 1 }">checked</c:if>><label for="cate_tent" onclick="checkbox_click(this)">텐트/타프</label>
					</li>
					<li class="obj_li <c:if test="${hash.get('테이블') == 1 || hash.get('체어') == 1 }">checked</c:if>">
						<input type="checkbox" style="display: none" name="category" value="#테이블#체어" id="cate_table" <c:if test="${hash.get('테이블') == 1 || hash.get('체어') == 1 }">checked</c:if>><label for="cate_table" onclick="checkbox_click(this)">테이블/체어</label>
					</li>
					<li class="obj_li <c:if test="${hash.get('취사') == 1 || hash.get('수납') == 1 }">checked</c:if>">
						<input type="checkbox" style="display: none" name="category" value="#취사#수납" id="cate_cooking" <c:if test="${hash.get('취사') == 1 || hash.get('수납') == 1 }">checked</c:if>><label for="cate_cooking" onclick="checkbox_click(this)">취사/수납</label>
					</li>
					<li class="obj_li <c:if test="${hash.get('랜턴') == 1 || hash.get('조명') == 1 }">checked</c:if>">
						<input type="checkbox" style="display: none" name="category" value="#랜턴#조명" id="cate_lantern" <c:if test="${hash.get('랜턴') == 1 || hash.get('조명') == 1 }">checked</c:if>><label for="cate_lantern" onclick="checkbox_click(this)">랜턴/조명</label>
					</li>
					<li class="obj_li <c:if test="${hash.get('소품') == 1 || hash.get('기타') == 1 }">checked</c:if>">
						<input type="checkbox" style="display: none" name="category" value="#소품#기타" id="cate_etc" <c:if test="${hash.get('소품') == 1 || hash.get('기타') == 1 }">checked</c:if>><label for="cate_etc" onclick="checkbox_click(this)">소품/기타</label>
					</li>
				</ul>
				
				<ul class="loc_ul">
				 	<li class="loc_li <c:if test="${hash.get('서울') == 1 }">checked</c:if>">
					    <input type="radio" style="display: none" name="category" id="option1" value="#서울" <c:if test="${hash.get('서울') == 1 }">checked</c:if>>
					    <label for="option1" onclick="radio_click(this)">서울</label>
				  	</li>
				  	<li class="loc_li <c:if test="${hash.get('경기') == 1 }">checked</c:if>">
				    	<input type="radio" style="display: none" name="category" id="option2" value="#경기" <c:if test="${hash.get('경기') == 1 }">checked</c:if>>
				   	 	<label for="option2" onclick="radio_click(this)">경기</label>
				  	</li>
				  	<li class="loc_li <c:if test="${hash.get('강원') == 1 }">checked</c:if>">
				    	<input type="radio" style="display: none" name="category" id="option3" value="#강원" <c:if test="${hash.get('강원') == 1 }">checked</c:if>>
				    	<label for="option3" onclick="radio_click(this)">강원</label>
				 	</li>
				  	<li class="loc_li <c:if test="${hash.get('충청') == 1 }">checked</c:if>">
				    	<input type="radio" style="display: none" name="category" id="option4" value="#충청" <c:if test="${hash.get('충청') == 1 }">checked</c:if>>
				    	<label for="option4" onclick="radio_click(this)">충청</label>
				  	</li>
				 	<li class="loc_li <c:if test="${hash.get('전라') == 1 }">checked</c:if>">
					    <input type="radio" style="display: none" name="category" id="option5" value="#전라" <c:if test="${hash.get('전라') == 1 }">checked</c:if>>
					    <label for="option5" onclick="radio_click(this)">전라</label>
				  	</li>
				  	<li class="loc_li <c:if test="${hash.get('경상') == 1 }">checked</c:if>">
					    <input type="radio" style="display: none" name="category" id="option6" value="#경상" <c:if test="${hash.get('경상') == 1 }">checked</c:if>>
					    <label for="option6" onclick="radio_click(this)">경상</label>
				  	</li>
				  	<li class="loc_li <c:if test="${hash.get('제주') == 1 }">checked</c:if>">	
					    <input type="radio" style="display: none" name="category" id="option7" value="#제주" <c:if test="${hash.get('제주') == 1 }">checked</c:if>>
					    <label for="option7" onclick="radio_click(this)">제주</label>
				  	</li>
				</ul>
			</div>
		</div>
		
		<hr class="hr0">
		
		
		<div id="write_content">
			<div class="text">제목</div>
			<input type="text" placeholder=" 제목을 입력하세요 " name="title" value="${dto.getTitle() }" class="title_input">		
			
			<hr class="hr">
			
			<div class="text">내용</div>		
			<textarea class="form-control" rows="7" name="content" placeholder=" 내용을 입력하세요  ">${dto.getContent() }</textarea>
			<div class="price_wrap">
				<input type="text" name="price" placeholder=" 가격을 입력하세요 (숫자만 입력) " value="${dto.getPrice() }" class="price">
			</div>
			<hr class="hr">
			
			<div class="text">해쉬태그</div>
			<input type="text" name="hashtag" class="hashtag" value="${dto.getHashtag()}">
			<hr class="hr">
			
		</div>
		
		<div class="bottom-container">
			<div class="img_label_wrap">		
				<label for="upfile" class="label">
					사진첨부
				</label>
				<label for="upfile" class="label">
					<img src="${ctxPath }/resources/images/icon/camera.png">
				</label>
			</div>
			<input type="file" style="display: none;" id="upfile" class="form-control-file border" name="upfile" multiple="multiple" onchange="fileUpload()">
			<div class="img_container">
			</div>
			<div class="non_file text">삭제할 사진 선택</div>
	  		<div class="row">
		  		<c:forEach var="file" items="${files}">
		  			<div class='upload_photo_file' >
		  				<input type="checkbox" name="deletefile" value="${file }" id="${file }">
		  				<label for="${file }"><img src="/finproj/resources/images/board/${dto.getPhoto_folder()}/${file }"></label>
		  			</div>
		  		</c:forEach>
	  		</div>
		</div>
		
		<div class="button-container">
			<input type="hidden" name="cm_no" value="${dto.getCm_no() }">
           	<input type="hidden" name="type" value="${dto.getType() }">
           	<input type="hidden" name="email" value="${dto.getEmail() }">
			<input type="submit" class="btn-primary" value="글수정">
			<input type="reset" class="btn-primary" value="초기화">
		</div>
	</form>
</div>