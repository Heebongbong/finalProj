<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<c:set var="dto" value="${Map.get('BoardDTO') }"/>
<c:set var="hash" value="${Map.get('HashMap') }"/>
<c:set var="files" value="${Map.get('Files') }"/>
<div class="market_write_wrap">
	<form onsubmit="return check()" id="updateform" method="post" enctype="multipart/form-data" action="${ctxPath }/market/updateform">
		<div class="form-group" align="left">
			<div>
				<input type="checkbox" value="#텐트#타프" id="cate_tent" class="category" onclick="cate_hash(this)" <c:if test="${hash.get('텐트') == 1 || hash.get('타프') == 1 }">checked</c:if>><label for="cate_tent">텐트/타프</label>
				<input type="checkbox" value="#테이블#체어" id="cate_table" class="category" onclick="cate_hash(this)" <c:if test="${hash.get('테이블') == 1 || hash.get('체어') == 1 }">checked</c:if>><label for="cate_table">테이블/체어</label>
				<input type="checkbox" value="#취사#수납" id="cate_cooking" class="category" onclick="cate_hash(this)" <c:if test="${hash.get('취사') == 1 || hash.get('수납') == 1 }">checked</c:if>><label for="cate_cooking">취사/수납</label>
				<input type="checkbox" value="#랜턴#조명" id="cate_lantern" class="category" onclick="cate_hash(this)" <c:if test="${hash.get('랜턴') == 1 || hash.get('조명') == 1 }">checked</c:if>><label for="cate_lantern">랜턴/조명</label>
				<input type="checkbox" value="#소품#기타" id="cate_etc" class="category" onclick="cate_hash(this)" <c:if test="${hash.get('소품') == 1 || hash.get('기타') == 1 }">checked</c:if>><label for="cate_etc">소품/기타</label>
			</div>
			<div>
				<select id="subject" onchange="loc_hash()">
					<option value="서울" <c:if test="${hash.get('서울') == 1 }">selected</c:if>>서울</option>
					<option value="인천" <c:if test="${hash.get('인천') == 1 }">selected</c:if>>인천</option>
					<option value="경기" <c:if test="${hash.get('경기') == 1 }">selected</c:if>>경기</option>
					<option value="강원" <c:if test="${hash.get('강원') == 1 }">selected</c:if>>강원</option>
					<option value="충청" <c:if test="${hash.get('충청') == 1 }">selected</c:if>>충청</option>
					<option value="전라" <c:if test="${hash.get('전라') == 1 }">selected</c:if>>전라</option>
					<option value="경상" <c:if test="${hash.get('경상') == 1 }">selected</c:if>>경상</option>
					<option value="제주" <c:if test="${hash.get('제주') == 1 }">selected</c:if>>제주</option>
				</select>
				<input type="text" placeholder="제목 : " name="title" value="${dto.getTitle() }">
			</div>
		</div>
		<div class="form-group" align="left">
			<textarea class="form-control" rows="15" name="content" placeholder="내용 : ">${dto.getContent() }</textarea><br>
			<input type="text" name="price" placeholder="가격 : " value="${dto.getPrice() }">
		</div>
		<div class="form-group" align="left">
			<label for="upfile">/ 파일 추가 버튼 수정 /</label>
			<input type="file" style="display: none" id="upfile" class="form-control-file border" name="upfile" multiple="multiple">
		</div>
		<div class="form-group" align="left">
           		<span>존재하는 사진 파일</span>
           		<div class="row">
	          		<c:forEach var="file" items="${files}">
	          			<div class='upload_photo_file col-4' >
	          				<input type="checkbox" name="deletefile" value="${file }">
	          				<img src="/finproj/resources/images/board/${dto.getPhoto_folder()}/${file }">
	          			</div>
	          		</c:forEach>
           		</div>
           </div>
		<div>
			<input type="text" name="hashtag" class="hashtag" value="${dto.getHashtag()}">
		</div>
		<div>
			<input type="hidden" name="cm_no" value="${dto.getCm_no() }">
           	<input type="hidden" name="type" value="${dto.getType() }">
           	<input type="hidden" name="email" value="${dto.getEmail() }">
			<input type="submit" class="" value="글작성">
			<input type="reset" class="" value="초기화">
		</div>
	</form>
</div>