<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctxPath" value="<%=request.getContextPath()%>" />
<c:set value="<%=request.getContextPath()%>" var="ctxPath" />
<c:set value="${sessionScope.LoginUser }" var="loginUser" />
<c:set value="${UserList }" var="boardList" />
<c:set value="${BoardList }" var="boardList" />
<c:set value="${Photoes }" var="photoes" />
<link rel="stylesheet" href="${ctxPath }/resources/css/slick/slick.css">
<link rel="stylesheet"
	href="${ctxPath }/resources/css/slick/slick-theme.css">
<div id="list_wrap">
	<c:if test="${!empty boardList }">
		<c:forEach items="${boardList }" var="board">
			<div>
				<c:set value="${board.getPhoto_files() }" var="files" />
				<p>${board.getCm_no() }</p>
				<p>${board.getUser_no() }</p>
				<p>${board.getContent() }</p>
				<p>${board.getHashtag() }</p>
				<c:if test="${!empty files }">
					<c:forEach items="${files }" var="file">
						<img
							src="${ctxPath }/resources/images/board/${board.getUser_email}/${files}/${file}.jpg">
					</c:forEach>
				</c:if>
				<c:if test="${empty files }">
					<p>파일이 없습니다.</p>
				</c:if>

			</div>

			<!-- 댓글 -->
			<div id="reply_card${board.getCm_no() }">
				<section class="modal-section">
					<div class="card card-body">
						<!-- 댓글 목록 -->
						<div class="reply-list reply-list${board.getCm_no() }">
							<!-- 댓글이 목록이 들어가는 곳 -->
						</div>
						<!-- 댓글 작성 => 로그인한 상태여야만 댓글작성 칸이 나온다. -->
						<c:if test="${!empty LoginUser }">
							<div class="row reply_write">
								<div >
									<a href="other_profile.do?other_nick=${metion.getUser_no }"> <img
										id="profileImage" src="./upload/profile/${user_no}" />
									</a>
								</div>
								<div>
									<input class="w-100 form-control" id="input_reply"
										type="text" placeholder="0">
								</div>
								<div>
									<button type="button" idx="${mention.getMention_no }"
										class="btn btn-success mb-1 write_reply">&nbsp;</button>
								</div>
							</div>
						</c:if>
					</div>
				</section>
			</div>
		</c:forEach>
	</c:if>
</div>