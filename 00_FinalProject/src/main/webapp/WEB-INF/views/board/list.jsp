<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctxPath" value="<%=request.getContextPath()%>" />
<c:set value="<%=request.getContextPath()%>" var="ctxPath" />
<c:set value="${sessionScope.LoginUser }" var="loginUser" />
<c:set value="${BoardList }" var="boardList" />
<link rel="stylesheet" href="${ctxPath }/resources/css/slick/slick.css">
<link rel="stylesheet" href="${ctxPath }/resources/css/slick/slick-theme.css">
<link rel="stylesheet" href="${ctxPath }/resources/css/board/board.css">
<script type="text/javascript" src="${ctxPath }/resources/js/slick/slick.js"></script>
<div id="list_wrap">
	<ul class="list_navi">
		<li class="list_navi_li">
			<a href="${ctxPath }/board/list">전체목록</a>
		</li>
		<li class="list_navi_li">
			<a href="${ctxPath }/board/list?keyword=요리">요리</a>
		</li>
		<li class="list_navi_li">
			<a href="${ctxPath }/board/list?keyword=일상">일상</a>
		</li>
		<li class="list_navi_li">
			<a href="${ctxPath }/board/list?keyword=캠핑">캠핑</a>
		</li>
		<li class="list_navi_li">
			<a href="${ctxPath }/board/list?keyword=리뷰">리뷰</a>
		</li>
	</ul>
	<c:if test="${!empty boardList }">
		<c:forEach items="${boardList }" var="board">
			<div class="list_main">
				<div class="list_board">
					<input class="board_no" type="hidden" value="${board.getCm_no() }">
					<c:set value="${board.getPhoto_files() }" var="files" />
					<div class="board_user">
						<img src="${board.profile }" >
						<span>${board.nickname }</span>
					</div>
					<div class="list_board_files">
						<c:if test="${!empty files }">
							<c:forEach items="${files }" var="file">
								<div class="photo_file">
									<img style="width: 400px" src="${ctxPath }/resources/images/board/${board.photo_folder }/${file}">
								</div>
							</c:forEach>
						</c:if>
						<c:if test="${empty files }">
							<p>파일이 없습니다.</p>
						</c:if>
					</div>
					<div class="board_content">
						<span>${board.getContent() }</span>
						<p>${board.getHashtag() }</p>
					</div>
					
					<!-- 댓글 -->
					<div id="reply_card${board.getCm_no() }">
						<div class="card card-body">
							<!-- 댓글 목록 -->
							<div class="reply-list reply-list${board.getCm_no() }">
								<!-- 댓글이 목록이 들어가는 곳 -->
							</div>
							<!-- 댓글 작성 => 로그인한 상태여야만 댓글작성 칸이 나온다. -->
							<c:if test="${!empty loginUser }">
								<div class="row reply_write">
									<div>
											<img id="profileImage" src="${loginUser.profile}" />
									</div>
									<div>
										<textarea id="w3review" name="w3review" rows="4" cols="50"></textarea>
									</div>
									<div>
										<button type="button" id="${mention.getMention_no }"
											class="btn btn-success mb-1 write_reply">댓글입력</button>
									</div>
								</div>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>
	</c:if>

</div>