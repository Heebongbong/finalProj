<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="<%=request.getContextPath() %>" var="ctxPath"/>
<c:set value="${sessionScope.LoginUser }" var="loginUser"/>
<c:set value="${MarketList.get('BoardList') }" var="boardList" />
<c:set value="${MarketList.get('MentionList') }" var="mentionList" />
<div id="market_wrap">
	<div class="market_navi">
		<div class="market_navi_cate">
			<a href="javascript:open_navi(0)">지역별</a>
			<a href="javascript:open_navi(1)">상품별</a>
		</div>
		<div class="market_navi_cont">
			<div class="cate_cont">
				<ul class="loc_ul">
					<li class="loc_li">
						<a href="javascript:open_navi_loc(0)">서울  / 인천</a>
						<ul class="loc_cont">
							<li>
								<a href="${ctxPath }/market/list?keyword=서울">서울</a>
							</li>
							<li>
								<a href="${ctxPath }/market/list?keyword=인천">인천</a>
							</li>
						</ul>
					</li>
					<li class="loc_li">
						<a href="javascript:open_navi_loc(1)">경기도 / 강원도</a>
						<ul class="loc_cont">
							<li>
								<a href="${ctxPath }/market/list?keyword=경기">경기도</a>
							</li>
							<li>
								<a href="${ctxPath }/market/list?keyword=강원">강원도</a>
							</li>
						</ul>
					</li>
					<li class="loc_li">
						<a href="javascript:open_navi_loc(2)">충청도 / 전라도</a>
						<ul class="loc_cont">
							<li>
								<a href="${ctxPath }/market/list?keyword=충청">충청도</a>
							</li>
							<li>
								<a href="${ctxPath }/market/list?keyword=전라">전라도</a>
							</li>
						</ul>
					</li>
					<li class="loc_li">
						<a href="javascript:open_navi_loc(3)">경상도 / 제주도</a>
						<ul class="loc_cont">
							<li>
								<a href="${ctxPath }/market/list?keyword=경상">경상도</a>
							</li>
							<li>
								<a href="${ctxPath }/market/list?keyword=제주">제주</a>
							</li>
						</ul>
					</li>	
				</ul>
			</div>
			<div class="cate_cont">
				<ul class="obj_ul">
					<li class="obj_li">
						<a href="${ctxPath }/market/list">전체</a>
					</li>
					<li class="obj_li">
						<a href="${ctxPath }/market/list?keyword=%23텐트%23타프">텐트/타프</a>
					</li>
					<li class="obj_li">
						<a href="${ctxPath }/market/list?keyword=%23테이블%23체어">테이블/체어</a>
					</li>
					<li class="obj_li">
						<a href="${ctxPath }/market/list?keyword=%23취사%23수납">취사/수납</a>
					</li>
					<li class="obj_li">
						<a href="${ctxPath }/market/list?keyword=%23랜턴%23조명">랜턴/조명</a>
					</li>
					<li class="obj_li">
						<a href="${ctxPath }/market/list?keyword=%23소품%23기타">소품/기타</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<div class="market_write">
		<a href="${ctxPath }/market/write"><i class="fa fa-pencil" aria-hidden="true"></i></a>
	</div>
	<form class="market_search" action="" method="get">
		<input type="text" placeholder="검색어를 입력하세요." name="keyword"><input type="submit" value="검색">
	</form>
	<div class="market_main">
		<c:forEach items="${boardList }" var="board">
			
			<div>
				${dto }
				<div>
					<c:set value="${mentionList.get('dto.cm_no') }" var="meList"/>
					<c:if test="${!empty meList }">
					<c:forEach items="${meList }" var="m">
						${m }
					</c:forEach>
					</c:if>
				</div>
			</div>
			
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
		
	</div>
</div>