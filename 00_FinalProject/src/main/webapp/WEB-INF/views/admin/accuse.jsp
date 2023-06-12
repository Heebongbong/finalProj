<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<div id="accuse_wrap">
	<h2>신고 리스트 관리</h2>
	
	<div class="accuse_info_wrap">
		<div class="accuse_info_wrap_th">글번호</div>
		<div class="accuse_info_wrap_th">신고자<br> 닉네임</div>
		<div class="accuse_info_wrap_th">신고자<br> 이메일</div>
		<div class="accuse_info_wrap_th">피신고자 닉네임 </div>
		<div class="accuse_info_wrap_th">피신고자 이메일 </div>
		<div class="accuse_info_wrap_th">신고 사유</div>
		<div class="accuse_info_wrap_th">글 내용</div>
		<div class="accuse_info_wrap_th">해시태그</div>
		<div class="accuse_info_wrap_th">(마켓)<br>제목</div>
		<div class="accuse_info_wrap_th">(마켓)<br>가격</div>
		<div class="accuse_info_wrap_th">글삭제</div>
		
		<c:forEach items="${accuseList }" var="dto" varStatus="status">
			<div class="accuse_info_wrap_td">${dto.cm_no }</div>
			<div class="accuse_info_wrap_td">${dto.nickname }</div>
			<div class="accuse_info_wrap_td">${dto.email }</div>
			<c:forEach items="${accusedList}" var="eddto" varStatus="edStatus">
	            <c:if test="${edStatus.index eq status.index}">
	            	<div class="accuse_info_wrap_td">
	                	${eddto.nickname}
	                </div>
	            	<div class="accuse_info_wrap_td">
	                	${eddto.email}
	                </div>
	            </c:if>
	        </c:forEach>
			<div class="accuse_info_wrap_td">${dto.reason }</div>
			<div class="accuse_info_wrap_td">${dto.content }</div>
			<div class="accuse_info_wrap_td">${dto.hashtag }</div>
			<div class="accuse_info_wrap_td">
				<c:if test="${dto.title eq null}">
					-
				</c:if> 
				${dto.title }
			</div>
			<div class="accuse_info_wrap_td">
				<c:if test="${dto.price eq null}">
					-
				</c:if> 
				${dto.price }
			</div>
			<div class="accuse_info_wrap_td">
				<button id="btn" onclick="toggleModal('myModal_${status.index}')">삭제</button>

				<div id="myModal_${status.index}" style="display:none">
					<div>
				    	<div><a href="${ctxPath}/admin/board/delete?cm_no=${dto.cm_no }">전체삭제</a></div>
				    	<div><a href="${ctxPath}/admin/deleteAccuse?cm_no=<c:forEach items="${accusedList}" var="eddto" varStatus="edStatus"><c:if test="${edStatus.index eq status.index}">${eddto.cm_no }</c:if></c:forEach>&user_no=${dto.user_no}">신고삭제</a></div>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>	
</div>